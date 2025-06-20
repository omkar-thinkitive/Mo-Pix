package com.mopix.Mopix.Services.ServiceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mopix.Mopix.Entity.UserEntity;
import com.mopix.Mopix.Repository.UserRepo;
import com.mopix.Mopix.Security.JwtProvider;
import com.mopix.Mopix.Services.LoginService;
import com.mopix.Mopix.Services.UserDetailService;
import com.mopix.Mopix.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

//    @Autowired
//    private RestTemplate restTemplate;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JwtProvider jwtProvider;

    @Value("${spring.github.client-id}")
    private String gitHubClientId;

    @Value("${spring.github.client-secret}")
    private String gitHubClientSecret;

    @Value("${spring.google.client-id}")
    private String googleClientId;

    @Value("${spring.google.client-secret}")
    private String googleClientSecret;

    @Value("${spring.google.redirect-Uri}")
    private String redirectUri;

//    @Value("${facebook.client-id}")
//    private String facebookClientId;
//
//    @Value("${facebook.client-secret}")
//    private String facebookClientSecret;
//
//    @Value("${facebook.redirect-uri}")
//    private String facebookRedirectUri;

    private String facebookClientId = "";
    private String facebookClientSecret = "";
    private String facebookRedirectUri = "";





    private final RestTemplate restTemplate = new RestTemplate();


//    @Override
//    public String signUpWithGoogle(String code) {
//
//        String clientId = "";
//        String clientSecret = "";
//
//        try{
//            String tokenEndpoint = "https://oauth2.googleapis.com/token";
//            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//            params.add("code", code);
//            params.add("client_id", clientId);
//            params.add("client_secret", clientSecret);
//            params.add("redirect_uri", "http://localhost:8080");
//            params.add("grant_type", "authorization_code");
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
////            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//
//            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
//            ResponseEntity<Map> tokenResponse = restTemplate.postForEntity(tokenEndpoint, request, Map.class);
//            String idToken = (String) tokenResponse.getBody().get("id_token");
//            String userInfoUrl = "https://oauth2.googleapis.com/tokeninfo?id_token=" + idToken;
//
//            ResponseEntity<Map> userInfoResponse = restTemplate.getForEntity(userInfoUrl, Map.class);
//            if (userInfoResponse.getStatusCode() == HttpStatus.OK) {
//                Map<String, Object> userInfo = userInfoResponse.getBody();
//                String email = (String) userInfo.get("email");
//                UserDetails userDetails = null;
//                try{
//                    userDetails = userDetailService.loadUserByUsername(email);
//                }catch (Exception e){
//                    UserEntity userEntity = new UserEntity();
//                    userEntity.setUserName(email);
//                    userEntity.setPassword(passwordEncoder.encode(email));
//                    userRepo.save(userEntity);
//                }
//                String jwtToken = jwtUtil.generateToken(email);
//                return jwtToken;
//            }
//        }  catch (
//    HttpClientErrorException e) {
//        log.error("HTTP error: {}", e.getResponseBodyAsString(), e);
//            log.error("HTTP error: Status={}, Headers={}, Body={}",
//                    e.getStatusCode(),
//                    e.getResponseHeaders(),
//                    e.getResponseBodyAsString(),
//                    e);
//            throw new RuntimeException("Google authentication failed", e);
//    }
//        return "Error Occurred";
//    }

    @Override
    public String signUpWithGoogle(String code){
        Map<String, String> tokenResponse = getTokens(code);

        String idToken = tokenResponse.get("id_token");
        Map<String, Object> userInfo = null;
        try {
            userInfo = parseJwt(idToken);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        String email = (String) userInfo.get("email");
        String name = (String) userInfo.get("name");

        UserEntity userEntity = userRepo.findByUserName(email);
        if(userEntity == null){
            UserEntity user = new UserEntity();
            user.setUserName(email);
            user.setFirstname((String) userInfo.get("name"));
            user.setPassword(String.valueOf(UUID.randomUUID()));
            userRepo.save(user);
        }

        String jwtToken = jwtProvider.generateToken(email);
        return jwtToken;
    }

    private Map<String, Object> parseJwt(String jwt) throws JsonProcessingException {
        String[] parts = jwt.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(parts[1]), StandardCharsets.UTF_8);
        return new ObjectMapper().readValue(payload, new TypeReference<>() {});
    }

    private Map<String, String> getTokens(String code) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", code);
        body.add("client_id", googleClientId);
        body.add("client_secret", googleClientSecret);
        body.add("redirect_uri", redirectUri);
        body.add("grant_type", "authorization_code");

//        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
//        body.add("code", code);
//        body.add("client_id", clientId);
//        body.add("client_secret", clientSecret);
//        body.add("redirect_uri", redirectUri);
//        body.add("grant_type", "authorization_code");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        System.out.println(request.getBody());

        ResponseEntity<Map> response = restTemplate.postForEntity(
                "https://oauth2.googleapis.com/token", request, Map.class);

        Map<String, Object> map = response.getBody();
        return Map.of(
                "access_token", (String) map.get("access_token"),
                "id_token", (String) map.get("id_token")
        );
    }

    @Override
    public String signUpWithGitHub(String code) {
        String accessToken = getAccessToken(code);
        Map<String, Object> userData = getUserData(accessToken);
        String email = getVerifiedEmail(accessToken);
        System.out.println(email);

        if (email == null) throw new RuntimeException("Verified email not found");

        UserEntity userEntity = userRepo.findByUserName(email);
        if(userEntity == null){
            UserEntity user = new UserEntity();
            user.setUserName(email);
            user.setFirstname((String) userData.get("name"));
            userRepo.save(user);
        }

        String jwtToken = jwtProvider.generateToken(email);
        return jwtToken;
    }

    private String getAccessToken(String code) {
        String clientId = gitHubClientId;
        String clientSecret = gitHubClientSecret;
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(
                "https://github.com/login/oauth/access_token", request, Map.class);

        return (String) response.getBody().get("access_token");
    }

    private Map<String, Object> getUserData(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                "https://api.github.com/user",
                HttpMethod.GET,
                entity,
                Map.class
        );
        return response.getBody();
    }

    private String getVerifiedEmail(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                "https://api.github.com/user/emails",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {}
        );

        return response.getBody().stream()
                .filter(e -> Boolean.TRUE.equals(e.get("primary")) && Boolean.TRUE.equals(e.get("verified")))
                .map(e -> (String) e.get("email"))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String signUpWithFacebook(String code) {
        String accessToken = getFacebookAccessToken(code);
        Map<String, Object> userData = getFacebookUserData(accessToken);

        String email = (String) userData.get("email");

        if (email == null) throw new RuntimeException("Email not found from Facebook");

        UserEntity userEntity = userRepo.findByUserName(email);
        if (userEntity == null) {
            UserEntity user = new UserEntity();
            user.setUserName(email);
            user.setFirstname((String) userData.get("name"));
            userRepo.save(user);
        }

        String jwtToken = jwtProvider.generateToken(email);
        return jwtToken;
    }

    private String getFacebookAccessToken(String code) {
        String tokenUri = "https://graph.facebook.com/v12.0/oauth/access_token";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", facebookClientId);
        params.add("client_secret", facebookClientSecret);
        params.add("redirect_uri", facebookRedirectUri); // same used in Facebook dev app
        params.add("code", code);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(tokenUri, request, Map.class);
        return (String) response.getBody().get("access_token");
    }

    private Map<String, Object> getFacebookUserData(String accessToken) {
        String userInfoUri = "https://graph.facebook.com/me?fields=id,name,email";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                userInfoUri,
                HttpMethod.GET,
                entity,
                Map.class
        );

        return response.getBody();
    }
}
