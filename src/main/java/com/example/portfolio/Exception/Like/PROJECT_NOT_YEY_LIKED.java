package com.example.portfolio.Exception.Like;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PROJECT_NOT_YEY_LIKED {
    @Schema(description = "오류 코드", example = "1020")
    private Integer code;

    @Schema(description = "상태 코드", example = "400")
    private Integer status;

    @Schema(description = "메시지", example = "좋아요한 프로젝트가 아닙니다")
    private String message;
}