package com.example.portfolio.Exception.Global;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class HTTP_INTERNAL_SERVER_ERROR {

    @Schema(description = "오류 코드", example = "1000")
    private Integer code;

    @Schema(description = "상태 코드", example = "500")
    private Integer status;

    @Schema(description = "메시지", example = "서버 에러가 발생했습니다.")
    private String message;
}
