package com.example.portfolio.JWT;

import com.example.portfolio.Common.ErrorCode;
import com.example.portfolio.Domain.User;
import com.example.portfolio.Exception.Global.UserApplicationException;
import com.example.portfolio.Repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    JwtParser parser;

    @Autowired
    UserRepository userRepository;

    // 토큰의 만료 시간 (1시간)
    private static final long EXPIRATION_TIME = 360000000;

    @Value("${spring.jwt.secret}")
    private String jwtSecret;


    public Key getJwtSecret() {
        // 문자열을 바이트 배열로 변환하여 Key 형식으로 반환
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    // JWT 토큰 생성
    public String generateToken(String nickname) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);
        return Jwts.builder()
                .setSubject(nickname)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getJwtSecret())
                .compact();
    }

    // JWT 토큰의 유효성 검사
    public User validateToken(String token) {
        try {
            token = token.replaceAll("^\"|\"$", "");
            jwtSecret = jwtSecret.replaceAll("^\"|\"$", "");
            this.parser = Jwts.parserBuilder().setSigningKey(jwtSecret.getBytes()).build();
            Claims claims = parser.parseClaimsJws(token).getBody();
            String nickname = claims.getSubject(); // 토큰에서 이메일 추출
            User user = userRepository.findByNickname(nickname);
            return user;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new UserApplicationException(ErrorCode.TOKEN_AUTHENTICATION_ERROR);
        }
    }
}