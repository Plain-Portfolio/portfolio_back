package com.example.portfolio.Exception.User;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PASSWORD_IS_VALID {

    @Schema(description = "오류 코드", example = "1004")
    private Integer code;

    @Schema(description = "상태 코드", example = "400")
    private Integer status;

    @Schema(description = "메시지", example = "비밀번호 유효성 검사에 실패하였습니다.")
    private String message;
}
