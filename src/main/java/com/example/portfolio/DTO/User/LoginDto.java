package com.example.portfolio.DTO.User;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LoginDto {

    @Schema(description = "사용자 email", example = "eos0103@naver.com")
    private String email;

    @Schema(description = "사용자 password", example = "aos4050123!@#")
    private String password;

}