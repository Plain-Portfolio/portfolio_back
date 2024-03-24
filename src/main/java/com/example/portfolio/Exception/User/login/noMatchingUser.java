package com.example.portfolio.Exception.User.login;

import io.swagger.v3.oas.annotations.media.Schema;

public class noMatchingUser {
    @Schema(description = "오류 코드", example = "1007")
    private Integer code;

    @Schema(description = "상태 코드", example = "402")
    private Integer status;

    @Schema(description = "메시지", example = "[1007 - 이메일, 1008 - 비밀번호] 매칭되는 유저가 존재하지 않습니다.")
    private String message;
}

