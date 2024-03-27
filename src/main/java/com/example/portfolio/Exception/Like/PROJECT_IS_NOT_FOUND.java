package com.example.portfolio.Exception.Like;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PROJECT_IS_NOT_FOUND {
    @Schema(description = "오류 코드", example = "1018")
    private Integer code;

    @Schema(description = "상태 코드", example = "401")
    private Integer status;

    @Schema(description = "메시지", example = "해당 ID를 가진 프로젝트가 존재하지 않습니다")
    private String message;
}
