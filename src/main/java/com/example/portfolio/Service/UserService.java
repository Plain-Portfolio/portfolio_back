package com.example.portfolio.Service;

import com.example.portfolio.Common.ErrorCode;
import com.example.portfolio.DTO.LoginDto;
import com.example.portfolio.DTO.SignUpDto;
import com.example.portfolio.Domain.User;
import com.example.portfolio.Exception.Global.UserApplicationException;
import com.example.portfolio.JWT.JwtTokenProvider;
import com.example.portfolio.Repository.UserRepository;
import com.example.portfolio.response.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
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
    JwtTokenProvider jwtTokenProvider;

    @Transactional
    public User signUp (SignUpDto signUpDto) {
        userRepository.SignUpDuplicateCheck(signUpDto);
        userRepository.validateCheck(signUpDto);
        System.out.println("여기까지 오나?");
        User user = new User();
        user.setNickname(signUpDto.getNickname());
        user.setEmail(signUpDto.getEmail());
        System.out.println("여기까지 오나?2");
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(signUpDto.getPassword());
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
//        Map<String, Object> response = new HashMap<>();
        LoginResponse loginResponse = new LoginResponse();

        try {
            validateLoginDto(loginDto);
            System.out.println("여기까지도옴11111");
            User user = userRepository.findByEmail(loginDto.getEmail());
            if (user == null) {
                throw new UserApplicationException(ErrorCode.NO_MATCHING_USER_FOUND_WITH_EMAIL);
            }
            System.out.println("여기까지도옴222222");
            Long userId = user.getId();
            if (userRepository.isSamePassword(user.getPassword(), loginDto.getPassword())) {
                throw new UserApplicationException(ErrorCode.NO_MATCHING_USER_FOUND_WITH_PASSWORD);
            }
            System.out.println("여기까지도옴33333");
            System.out.println("여기까지도옴44444");
            String token = jwtTokenProvider.generateToken(user.getEmail());
            System.out.println("여기까지도옴55555");
            loginResponse.setToken(token);
            loginResponse.setUser(user);
            System.out.println(loginResponse);
            return loginResponse;
        } catch (Exception e) {
            throw new UserApplicationException(ErrorCode.TOKEN_AUTHENTICATION_ERROR);
        }
    }

}