package com.example.portfolio.Dto.Comment;

import lombok.Data;

@Data
public class UpdateCommentDto {

    private long commentId;

    private String context;
}
