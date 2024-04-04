package com.example.portfolio.DTO.User;

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
}