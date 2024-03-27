package com.example.portfolio.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SuccessResponse {

    @Schema(description = "성공 메시지", example = "200")
    private Integer status = 200;

    @Schema(description = "성공 메시지", example = "success")
    private String message = "success";
}
