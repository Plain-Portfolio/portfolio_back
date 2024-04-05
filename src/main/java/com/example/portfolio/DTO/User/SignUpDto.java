package com.example.portfolio.DTO.User;

import com.example.portfolio.Domain.UserImg;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
public class SignUpDto {

    @Schema(description = "사용자 email", example = "eos0103@naver.com")
    private String email;

    @Schema(description = "사용자 password", example = "aos4050123!@#")
    private String password;

    @Schema(description = "사용자 nickname", example = "김기범")
    private String nickname;

    @Schema(description = "사용자 소개", example = "안녕하세요 저는 React 개발자입니다")
    private String introduction;

    @Schema(description = "사용자 소개", example = "https://porfolioimage.s3.ap-northeast-2.amazonaws.com/d55b28d1-ffd2-406b-8da0-05acb578031d-%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202024-02-18%20132920.png")
    private UserImg userImg;
}