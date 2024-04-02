package com.example.portfolio.DTO.Comment;

import lombok.Data;

@Data
public class UpdateCommentDto {

    private Long commentId;
    private String context;
    private Long parentCommentOrderId;
    private Long commentOrder;
    private Integer childCommentCount;
}
