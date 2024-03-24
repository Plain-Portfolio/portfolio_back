package com.example.portfolio.Exception.User;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class NICKNAME_IS_DUPLICATED {
    @Schema(description = "오류 코드", example = "1002")
    private Integer code;

    @Schema(description = "상태 코드", example = "401")
    private Integer status;

    @Schema(description = "메시지", example = "닉네임이 중복되었습니다.")
    private String message;
}
