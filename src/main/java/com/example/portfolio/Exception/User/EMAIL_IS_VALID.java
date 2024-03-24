package com.example.portfolio.Exception.User;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class EMAIL_IS_VALID {

    @Schema(description = "오류 코드", example = "1003")
    private Integer code;

    @Schema(description = "상태 코드", example = "400")
    private Integer status;

    @Schema(description = "메시지", example = "이메일 유효성 검사에 실패하였습니다.")
    private String message;
}
