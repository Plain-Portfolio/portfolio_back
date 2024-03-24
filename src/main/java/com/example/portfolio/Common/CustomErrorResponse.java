package com.example.portfolio.Common;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@AllArgsConstructor
public class CustomErrorResponse {
    private Integer status;
    private Integer code;
    private String message;
}
