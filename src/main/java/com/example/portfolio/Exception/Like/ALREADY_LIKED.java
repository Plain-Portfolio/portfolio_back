package com.example.portfolio.Exception.Like;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ALREADY_LIKED {
    @Schema(description = "오류 코드", example = "1019")
    private Integer code;

    @Schema(description = "상태 코드", example = "402")
    private Integer status;

    @Schema(description = "메시지", example = "이미 좋아요한 유저입니다")
    private String message;
}
