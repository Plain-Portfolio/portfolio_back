package com.example.portfolio.Service;

import com.example.portfolio.Common.ErrorCode;
import com.example.portfolio.DTO.User.LoginDto;
import com.example.portfolio.DTO.User.SignUpDto;
import com.example.portfolio.Domain.User;
import com.example.portfolio.Domain.UserImg;
import com.example.portfolio.Exception.Global.UserApplicationException;
import com.example.portfolio.JWT.JwtTokenProvider;
import com.example.portfolio.Repository.UserImageRepository;
import com.example.portfolio.Repository.UserRepository;
import com.example.portfolio.response.User.LoginResponse;
import com.example.portfolio.response.User.UserResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    @Value("${spring.oauth2.google.client-id}")
    private String googleClientId;

    @Value("${spring.oauth2.google.client-secret}")
    private String googleClientSecret;

    @Value("${spring.oauth2.google.redirect-uri}")
    private String googleRedirectUri;

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
    public User signUp (SignUpDto signUpDto) {
        userRepository.SignUpDuplicateCheck(signUpDto);
        userRepository.validateCheck(signUpDto);
        System.out.println("여기까지 오나?");
        User user = new User();
        user.setNickname(signUpDto.getNickname());
        user.setEmail(signUpDto.getEmail());
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
                userImgList.add(findUserImg);

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
            if (userRepository.isSamePassword(user.getPassword(), loginDto.getPassword())) {
                throw new UserApplicationException(ErrorCode.NO_MATCHING_USER_FOUND_WITH_PASSWORD);
            }
            String token = jwtTokenProvider.generateToken(user.getEmail());
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