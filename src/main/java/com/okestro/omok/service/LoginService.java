package com.okestro.omok.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.okestro.omok.domain.User;
import com.okestro.omok.exception.ClientException;
import com.okestro.omok.exception.ErrorCode;
import com.okestro.omok.jwt.JwtTokenProvider;
import com.okestro.omok.payload.dto.TokenInfo;
import com.okestro.omok.payload.request.CreateUserRequest;
import com.okestro.omok.payload.response.UserTokenDetailsResponse;
import com.okestro.omok.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Transactional
public class LoginService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${oauth2.google.client-id}")
    private String googleClientId;

    @Value("${oauth2.google.client-secret}")
    private String googleClientSecret;

    @Value("${oauth2.google.redirect-uri}")
    private String googleRedirectUri;

    @Value("${oauth2.google.token-uri}")
    private String googleTokenUri;

    @Value("${oauth2.google.resource-uri}")
    private String googleResourceUri;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("@okestro.com");


    // Public Methods
    public UserTokenDetailsResponse socialLogin(String code) {
        String accessToken = getAccessToken(code);
        JsonNode userResourceNode = fetchUserResource(accessToken);
        validateEmailDomain(userResourceNode);

        return processUserLogin(userResourceNode);
    }

    public TokenInfo login(String email) {
        return jwtTokenProvider.generateToken(email);
    }

    // Private Helper Methods
    private String getAccessToken(String authorizationCode) {
        return exchangeForOAuthValue(googleTokenUri, createAccessTokenParams(authorizationCode)).get("access_token").asText();
    }

    private JsonNode fetchUserResource(String accessToken) {
        return exchangeForOAuthValue(googleResourceUri, createOAuthHeaders("Bearer " + accessToken));
    }

    private HttpHeaders createOAuthHeaders(String authValue) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authValue);
        return headers;
    }

    private MultiValueMap<String, String> createAccessTokenParams(String authorizationCode) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", authorizationCode);
        params.add("client_id", googleClientId);
        params.add("client_secret", googleClientSecret);
        params.add("redirect_uri", googleRedirectUri);
        params.add("grant_type", "authorization_code");
        return params;
    }

    private JsonNode exchangeForOAuthValue(String uri, Object requestEntity) {
        return restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(requestEntity), JsonNode.class).getBody();
    }

    private void validateEmailDomain(JsonNode userResource) {
        String email = userResource.get("email").asText();
        if (!EMAIL_PATTERN.matcher(email).find()) {
            throw new ClientException(ErrorCode.INVALID_EMAIL);
        }
    }

    private UserTokenDetailsResponse processUserLogin(JsonNode userResource) {
        String email = userResource.get("email").asText();
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent()) {
            return buildResponseForExistingUser(optionalUser.get());
        } else {
            return buildResponseForNewUser(userResource);
        }
    }

    private UserTokenDetailsResponse buildResponseForExistingUser(User existingUser) {
        User user = User.alreadyJoinUser(existingUser);
        TokenInfo tokenInfo = login(user.getEmail());
        return UserTokenDetailsResponse.toEntity(user, tokenInfo.getAccessToken());
    }

    private UserTokenDetailsResponse buildResponseForNewUser(JsonNode userResource) {
        String email = userResource.get("email").asText();
        String name = userResource.get("name").asText();
        String image = userResource.get("picture").asText();

        User user = User.toEntity(new CreateUserRequest(email, name, image));
        User savedUser = userRepository.save(user);

        TokenInfo tokenInfo = login(savedUser.getEmail());
        return UserTokenDetailsResponse.toEntity(savedUser, tokenInfo.getAccessToken());
    }
}