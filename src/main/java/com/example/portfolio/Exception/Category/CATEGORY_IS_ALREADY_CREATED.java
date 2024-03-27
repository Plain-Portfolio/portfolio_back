package com.example.portfolio.Exception.Category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CATEGORY_IS_ALREADY_CREATED {
    @Schema(description = "오류 코드", example = "1021")
    private Integer code;

    @Schema(description = "상태 코드", example = "400")
    private Integer status;

    @Schema(description = "메시지", example = "이미 해당 이름을 가진 카테고리가 존재합니다")
    private String message;
}
