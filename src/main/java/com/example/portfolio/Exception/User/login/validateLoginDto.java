package com.example.portfolio.Exception.User.login;

import io.swagger.v3.oas.annotations.media.Schema;

public class validateLoginDto {
    @Schema(description = "오류 코드", example = "1006")
    private Integer code;

    @Schema(description = "상태 코드", example = "400")
    private Integer status;

    @Schema(description = "메시지", example = "유효성 검사 실패 - Null, 이메일 형식 체크")
    private String message;
}
