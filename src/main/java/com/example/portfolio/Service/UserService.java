package com.example.portfolio.Service;

import com.example.portfolio.Common.ErrorCode;
import com.example.portfolio.DTO.User.LoginDto;
import com.example.portfolio.DTO.User.SignUpDto;
import com.example.portfolio.DTO.User.SocialLoginCallBackDto;
import com.example.portfolio.Domain.User;
import com.example.portfolio.Domain.UserImg;
import com.example.portfolio.Exception.Global.UserApplicationException;
import com.example.portfolio.JWT.JwtTokenProvider;
import com.example.portfolio.Repository.UserImageRepository;
import com.example.portfolio.Repository.UserRepository;
import com.example.portfolio.response.User.LoginResponse;
import com.example.portfolio.response.User.SocialLoginRes;
import com.example.portfolio.response.User.UserResponseDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;
import java.util.regex.Pattern;

@Service
public class UserService {

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MAX_PASSWORD_LENGTH = 20;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserImageRepository imageRepository;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    //google
    @Value("${spring.oauth2.google.client-id}")
    private String googleClientId;
    @Value("${spring.oauth2.google.client-secret}")
    private String googleClientSecret;
    @Value("${spring.oauth2.google.redirect-uri}")
    private String googleRedirectUri;


    //kakao
    @Value("${spring.oauth2.kakao.redirect-uri}")
    private String kakaoRedirectUri;
    @Value("${spring.oauth2.kakao.client-id}")
    private String kakaoClientId;


    //naver
    @Value("${spring.oauth2.naver.client-id}")
    private String naverClientId;

    @Value("${spring.oauth2.naver.redirect-uri}")
    private String naverRedirectUri;

    @Value("${spring.oauth2.naver.client-secret}")
    private String naverClientSecret;

    public String naverLogin() {
        String authUrl = "https://nid.naver.com/oauth2.0/authorize";
        String url = UriComponentsBuilder.fromHttpUrl(authUrl)
                .queryParam("client_id", naverClientId)
                .queryParam("redirect_uri", naverRedirectUri)
                .queryParam("response_type", "code")
                .queryParam("state", "abcdef123456")
                .build().toUriString();
        return url;
    }

    public String kakaoLogin() {
        String authUrl = "https://kauth.kakao.com/oauth/authorize";
        String url = UriComponentsBuilder.fromHttpUrl(authUrl)
                .queryParam("client_id", kakaoClientId)
                .queryParam("redirect_uri", kakaoRedirectUri)
                .queryParam("response_type", "code")
                .build().toUriString();
        return url;
    }

    public MultiValueMap<String, String> accessTokenParams(String grantType,String clientSecret, String clientId,String code,String redirect_uri) {
        MultiValueMap<String, String> accessTokenParams = new LinkedMultiValueMap<>();
        accessTokenParams.add("grant_type", grantType);
        accessTokenParams.add("client_id", clientId);
        accessTokenParams.add("client_secret", clientSecret);
        accessTokenParams.add("code", code); // 응답으로 받은 코드
        accessTokenParams.add("redirect_uri", redirect_uri);
        return accessTokenParams;
    }

    public SocialLoginRes testNaverLoginCallBack (String code) throws Exception {

        String tokenUrl = "https://nid.naver.com/oauth2.0/token";
        String userInfoUrl = "https://openapi.naver.com/v1/nid/me";

        // 토큰 요청 파라미터 설정
        MultiValueMap<String, String> tokenParams = new LinkedMultiValueMap<>();
        tokenParams.add("grant_type", "authorization_code");
        tokenParams.add("client_id", naverClientId);
        tokenParams.add("client_secret", naverClientSecret);
        tokenParams.add("redirect_uri", naverRedirectUri);
        tokenParams.add("code", code);

        HttpHeaders tokenHeaders = new HttpHeaders();
        tokenHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> tokenRequest = new HttpEntity<>(tokenParams, tokenHeaders);

        // 토큰 요청
        ResponseEntity<String> tokenResponse = new RestTemplate().postForEntity(tokenUrl, tokenRequest, String.class);
        if (tokenResponse.getStatusCode() != HttpStatus.OK) {
            throw new Exception("네이버 토큰 인증 실패");
        }

        // 토큰 응답에서 액세스 토큰 추출
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode tokenJson = objectMapper.readTree(tokenResponse.getBody());
        String accessToken = tokenJson.get("access_token").asText();

        // 사용자 정보 요청 헤더 설정
        HttpHeaders userInfoHeaders = new HttpHeaders();
        userInfoHeaders.set("Authorization", "Bearer " + accessToken);

        // 사용자 정보 요청
        HttpEntity<String> userInfoRequest = new HttpEntity<>(userInfoHeaders);
        ResponseEntity<String> userInfoResponse = new RestTemplate().exchange(userInfoUrl, HttpMethod.GET, userInfoRequest, String.class);
        if (userInfoResponse.getStatusCode() != HttpStatus.OK) {
            throw new Exception("네이버 사용자 정보 요청 실패");
        }

        // 사용자 정보 추출
        JsonNode userInfoJson = objectMapper.readTree(userInfoResponse.getBody());
        String userEmail = userInfoJson.path("response").path("email").asText();
        String userName = userInfoJson.path("response").path("nickname").asText();

        // 사용자가 이미 등록되어 있는지 확인
        if (userRepository.countUserByNickname(userName) != 0) {
            User findUser = userRepository.findByNickname(userName);
            SocialLoginRes res = new SocialLoginRes(findUser);
            return res;
        }

        // 사용자 등록
        User user = new User();
        user.setEmail(userEmail);
        user.setNickname(userName);
        userRepository.save(user);
        SocialLoginRes res = new SocialLoginRes(user);

        return res;
    }

    @Transactional
    public SocialLoginRes naverLoginCallBack (SocialLoginCallBackDto socialLoginCallBackDto) throws Exception {
        User user = new User();
        if (socialLoginCallBackDto.getEmail() != null) {
            user.setEmail(socialLoginCallBackDto.getEmail());
        }
        if (userRepository.countUserByNickname(socialLoginCallBackDto.getNickname()) != 0) {
            User findUser = userRepository.findByNickname(socialLoginCallBackDto.getNickname());
            SocialLoginRes res = new SocialLoginRes(findUser);
            String token = jwtTokenProvider.generateToken(socialLoginCallBackDto.getNickname());
            res.setToken(token);
            return res;
        }

        user.setNickname(socialLoginCallBackDto.getNickname());
        userRepository.save(user);
        String token = jwtTokenProvider.generateToken(socialLoginCallBackDto.getNickname());
        SocialLoginRes res = new SocialLoginRes(user);
        System.out.println(token + "확인?????");
        res.setToken(token);

        return res;
    }

    @Transactional
    public SocialLoginRes kakaoLoginCallBack (SocialLoginCallBackDto socialLoginCallBackDto) throws Exception {
        User user = new User();
        if (socialLoginCallBackDto.getEmail() != null) {
            user.setEmail(socialLoginCallBackDto.getEmail());
        }
        if (userRepository.countUserByNickname(socialLoginCallBackDto.getNickname()) != 0) {
            User findUser = userRepository.findByNickname(socialLoginCallBackDto.getNickname());
            SocialLoginRes res = new SocialLoginRes(findUser);
            String token = jwtTokenProvider.generateToken(socialLoginCallBackDto.getNickname());
            res.setToken(token);
            return res;
        }

        user.setNickname(socialLoginCallBackDto.getNickname());
        userRepository.save(user);

        String token = jwtTokenProvider.generateToken(socialLoginCallBackDto.getNickname());
        SocialLoginRes res = new SocialLoginRes(user);
        res.setToken(token);

        return res;
//        String tokenUrl = "https://kauth.kakao.com/oauth/token";
//        String userInfoUrl = "https://kapi.kakao.com/v2/user/me";
//
//        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
//        parameters.add("grant_type", "authorization_code");
//        parameters.add("client_id", kakaoClientId);
//        parameters.add("redirect_uri", kakaoRedirectUri);
//        parameters.add("code", code);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(parameters, headers);
//
//        ResponseEntity<String> response = new RestTemplate().postForEntity(tokenUrl, request, String.class);
//        if (response.getStatusCode() != HttpStatus.OK) {
//            throw new Exception("카카오 토큰 인증 실패");
//        }
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode jsonResponse = objectMapper.readTree(response.getBody());
//        String accessToken = jsonResponse.get("access_token").asText();
//
//        HttpHeaders userInfoHeaders = new HttpHeaders();
//        userInfoHeaders.set("Authorization", "Bearer " + accessToken);
//        HttpEntity<String> userInfoRequest = new HttpEntity<>(userInfoHeaders);
//
//        ResponseEntity<String> userInfoResponse = new RestTemplate().exchange(userInfoUrl, HttpMethod.GET, userInfoRequest, String.class);
//        if (userInfoResponse.getStatusCode() != HttpStatus.OK) {
//            throw new Exception("카카오 사용자 정보 요청 실패");
//        }
//        System.out.println("여기까지 오나");
//
//        JsonNode userInfoJson = objectMapper.readTree(userInfoResponse.getBody());
//        System.out.println(userInfoJson + "어디 보자");
//        String userEmail = userInfoJson.path("kakao_account").path("email").asText();
//        String userName = userInfoJson.path("properties").path("nickname").asText();
//
//        System.out.println(userName + "Email 테스트");
//        System.out.println(userRepository.countUserByNickname(userName));
//        if (userRepository.countUserByNickname(userName) != 0) {
//            User findUser = userRepository.findByNickname(userName);
//            SocialLoginRes res = new SocialLoginRes(findUser);
//            return res;
//        }
//        System.out.println("어디서 발생했냐");
//        User user = new User();
//        user.setEmail(userEmail);
//        user.setNickname(userName);
//        userRepository.save(user);
//        SocialLoginRes res = new SocialLoginRes(user);
//
//        return res;
    }

    public String googleLogin () {

        String authUrl = "https://accounts.google.com/o/oauth2/auth";
        String redirectUri = googleRedirectUri;
        String scope = "email profile";
        String state = "state";

        String url = UriComponentsBuilder.fromHttpUrl(authUrl)
                .queryParam("client_id", googleClientId)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("scope", scope)
                .queryParam("state", state)
                .queryParam("response_type", "code")
                .build().toUriString();
        return url;
    }

    @Transactional
    public SocialLoginRes googleLoginCallBack (String code) throws Exception {
        String tokenUrl = "https://oauth2.googleapis.com/token";
        String userInfoUrl = "https://www.googleapis.com/oauth2/v3/userinfo";

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("code", code);
        parameters.add("client_id", googleClientId);
        parameters.add("client_secret", googleClientSecret);
        parameters.add("redirect_uri", googleRedirectUri);
        parameters.add("grant_type", "authorization_code");

        ResponseEntity<String> response = new RestTemplate().postForEntity(tokenUrl, parameters, String.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new Exception("google 토큰 인증 실패");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String responseBody = response.getBody();
        JsonNode jsonResponse = objectMapper.readTree(response.getBody());
        String accessToken = jsonResponse.get("access_token").asText();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> userInfoResponse = new RestTemplate().exchange(userInfoUrl, HttpMethod.GET, entity, String.class);
        if (userInfoResponse.getStatusCode() != HttpStatus.OK) {
            throw new Exception("사용자 정보 요청 실패");
        }
        JsonNode userInfoJson = objectMapper.readTree(userInfoResponse.getBody());
        String userEmail = userInfoJson.get("email").asText();
        String userName = userInfoJson.get("name").asText();
        if (userRepository.countUserByEmail(userEmail) != 0) {
            User findUser = userRepository.findByEmail(userEmail);
            SocialLoginRes res = new SocialLoginRes(findUser);
            System.out.println("적용 됐나");
            return res;
        }
        User user = new User();
        user.setEmail(userEmail);
        user.setNickname(userName);
        userRepository.save(user);
        String token = jwtTokenProvider.generateToken(userName);
        SocialLoginRes res = new SocialLoginRes(user);
        res.setToken(token);

        return res;

//        User user = new User();
//        if (socialLoginCallBackDto.getEmail() != null) {
//            user.setEmail(socialLoginCallBackDto.getEmail());
//        }
//        if (userRepository.countUserByNickname(socialLoginCallBackDto.getNickname()) != 0) {
//            User findUser = userRepository.findByNickname(socialLoginCallBackDto.getNickname());
//            SocialLoginRes res = new SocialLoginRes(findUser);
//            return res;
//        }
//
//        user.setNickname(socialLoginCallBackDto.getNickname());
//        userRepository.save(user);
//        SocialLoginRes res = new SocialLoginRes(user);
//
//        return res;


    }

    @Transactional
    public User signUp (SignUpDto signUpDto) {
        userRepository.SignUpDuplicateCheck(signUpDto);
        userRepository.validateCheck(signUpDto);
        System.out.println("여기까지 오나?");
        User user = new User();
        user.setNickname(signUpDto.getNickname());
        user.setEmail(signUpDto.getEmail());
        userRepository.save(user);
        if (signUpDto.getUserImgs() != null) {
            Set<Long> userImgids = new HashSet<>();
            for (SignUpDto.SignupUserImg userImg : signUpDto.getUserImgs()) {
                 userImgids.add(userImg.getUserImgId());
            }
            if (userImgids.size() != signUpDto.getUserImgs().size()) {
                throw new UserApplicationException(ErrorCode.DUPLICATE_IMAGE_EXISTS);
            }

            List<UserImg> userImgList = new ArrayList<>();
            for (SignUpDto.SignupUserImg userImg : signUpDto.getUserImgs()) {

                UserImg findUserImg = imageRepository.findUserImgByUserImgId(userImg.getUserImgId());
                if (findUserImg.getOwner() != null) {
                    throw new UserApplicationException(ErrorCode.ALREADY_OWNER_IMAGE);
                }
                userImgList.add(findUserImg);
                findUserImg.setOwner(user);
                imageRepository.save(findUserImg);

            }

            user.setUserImgs(userImgList);

        }
        System.out.println("여기까지 오나?2");
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(signUpDto.getPassword());
        if(signUpDto.getIntroduction() != null) {
            user.setIntroduction(signUpDto.getIntroduction());
        }

        System.out.println("여기까지 오나?3");
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return user;
    }

    public void validateLoginDto (LoginDto loginDto) {
        if (loginDto.getEmail() == null) {
            throw new UserApplicationException(ErrorCode.EMAIL_CANNOT_BE_NULL);
        }
        if (!Pattern.matches(EMAIL_PATTERN, loginDto.getEmail())) {
            throw new UserApplicationException(ErrorCode.EMAIL_IS_VALID);
        }
        if (loginDto.getPassword() == null ||
                loginDto.getPassword().length() < MIN_PASSWORD_LENGTH ||
                loginDto.getPassword().length() > MAX_PASSWORD_LENGTH) {
            throw new UserApplicationException(ErrorCode. PASSWORD_IS_VALID);
        }
    }

    @Transactional
    public LoginResponse login (LoginDto loginDto) {

        try {
            System.out.println("?????");
            validateLoginDto(loginDto);
            User user = userRepository.findByEmail(loginDto.getEmail());
            if (user == null) {
                throw new UserApplicationException(ErrorCode.NO_MATCHING_USER_FOUND_WITH_EMAIL);
            }
            System.out.println("여기까지도옴222222");
            Long userId = user.getId();
            if (userRepository.isSamePassword(loginDto.getPassword(), user.getPassword()) == false) {
                throw new UserApplicationException(ErrorCode.NO_MATCHING_USER_FOUND_WITH_PASSWORD);
            }
            String token = jwtTokenProvider.generateToken(user.getNickname());
            LoginResponse response = new LoginResponse(user);
            response.setToken(token);
            return response;
        } catch (Exception e) {
            throw new UserApplicationException(ErrorCode.TOKEN_AUTHENTICATION_ERROR);
        }
    }

    @Transactional
    public List<User> findUserList () {
        List<User> users = userRepository.findAllUser();

        return users;
    }

}