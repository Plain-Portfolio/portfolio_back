package com.example.portfolio.DTO.Comment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CreateCommentDto {

    private String context;

    private Long projectId;
}