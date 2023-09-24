package com.okestro.omok.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.okestro.omok.domain.User;
import com.okestro.omok.exception.ClientException;
import com.okestro.omok.exception.ErrorCode;
import com.okestro.omok.jwt.JwtTokenProvider;
import com.okestro.omok.payload.dto.TokenInfo;
import com.okestro.omok.payload.request.CreateUserRequest;
import com.okestro.omok.payload.response.UserDetailsResponse;
import com.okestro.omok.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
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

    private final JwtTokenProvider jwtTokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

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
            TokenInfo tokenInfo = login(email);
            System.out.println("이메일 = " + email);
            System.out.println("토큰 인포 = " + tokenInfo);
            return UserDetailsResponse.toEntity(user, tokenInfo.getAccessToken());
        }

        User user = User.toEntity(createUserRequest);

        User saveUser = userRepository.save(user);

        TokenInfo tokenInfo = login(email);
        System.out.println("이메일 = " + email);
        System.out.println("토큰 인포 = " + tokenInfo);

        return UserDetailsResponse.toEntity(saveUser, tokenInfo.getAccessToken());
    }

    public TokenInfo login(String email) {

        // Spring Security는 사용자 검증을 위해
        // encoding된 password와 그렇지 않은 password를 비교

        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, null);

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
//        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        TokenInfo tokenInfo = jwtTokenProvider.generateToken(email);

        return tokenInfo;
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
        System.out.println("엑세스 토큰 노드 : " + accessTokenNode);
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