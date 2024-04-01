package com.example.portfolio.DTO.Comment;

import lombok.Data;

@Data
public class UpdateCommentDto {

    private long commentId;

    private String context;
}
