package com.okestro.omok.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.okestro.omok.domain.User;
import com.okestro.omok.exception.ClientException;
import com.okestro.omok.exception.ErrorCode;
import com.okestro.omok.payload.request.CreateUserRequest;
import com.okestro.omok.payload.response.UserDetailsResponse;
import com.okestro.omok.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Transactional
public class LoginService {

    private final UserRepository userRepository;

    private final Environment env;
    private final RestTemplate restTemplate = new RestTemplate();

    public UserDetailsResponse socialLogin(String code, String registrationId) {
        System.out.println("Authorization code = " + code);
        System.out.println("registrationId = " + registrationId);
        String accessToken = getAccessToken(code, registrationId);
        System.out.println("AccessToken = " + accessToken);

        JsonNode userResourceNode = getUserResource(accessToken, registrationId);
        System.out.println("userResourceNode = " + userResourceNode);

        String id = userResourceNode.get("id").asText();
        String email = userResourceNode.get("email").asText();
        String name = userResourceNode.get("name").asText();
        String image = userResourceNode.get("picture").asText();

        System.out.println("id = " + id);
        System.out.println("email = " + email);
        System.out.println("nickname = " + name);

        CreateUserRequest createUserRequest = new CreateUserRequest(email, name, image);

        isValidEmail(email);

        Optional<User> existUser = userRepository.findByEmail(email);

        // 이미 가입한 유저인 경우
        if(existUser.isPresent()) {
            User user = User.alreadyJoinUser(existUser.get());
            return UserDetailsResponse.toEntity(user);
        }

        User user = User.toEntity(createUserRequest,code);

        User saveUser = userRepository.save(user);

        return UserDetailsResponse.toEntity(saveUser);
    }

    private String getAccessToken(String authorizationCode, String registrationId) {
        String clientId = env.getProperty("oauth2." + registrationId + ".client-id");
        String clientSecret = env.getProperty("oauth2." + registrationId + ".client-secret");
        String redirectUri = env.getProperty("oauth2." + registrationId + ".redirect-uri");
        String tokenUri = env.getProperty("oauth2." + registrationId + ".token-uri");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", authorizationCode);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("grant_type", "authorization_code");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity entity = new HttpEntity(params, headers);

        ResponseEntity<JsonNode> responseNode = restTemplate.exchange(tokenUri, HttpMethod.POST, entity, JsonNode.class);
        JsonNode accessTokenNode = responseNode.getBody();
        System.out.println(accessTokenNode);
        return accessTokenNode.get("access_token").asText();
    }

    private JsonNode getUserResource(String accessToken, String registrationId) {
        String resourceUri = env.getProperty("oauth2."+registrationId+".resource-uri");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity entity = new HttpEntity(headers);
        return restTemplate.exchange(resourceUri, HttpMethod.GET, entity, JsonNode.class).getBody();
    }

    private void isValidEmail(String email) {
        String pattern = "@okestro.com";

        Pattern regex = Pattern.compile(pattern);

        Matcher matcher = regex.matcher(email);

        if(!matcher.find()) {
            throw new ClientException(ErrorCode.INVALID_EMAIL);
        }
    }
}