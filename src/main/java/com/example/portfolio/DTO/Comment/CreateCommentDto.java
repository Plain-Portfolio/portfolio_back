package com.example.portfolio.Dto.Comment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CreateCommentDto {

    @Schema(description = "댓글 text", example = "댓글1")
    private String context;

    @Schema(description = "댓글을 달 프로젝트 ID", example = "1")
    private Long projectId;

    @Schema(description = "댓글 부모 ID", example = "1")
    private Long parentCommentOrderId;

    @Schema(description = "댓글 순서", example = "2")
    private Long commentOrder;

    @Schema(description = "자식 댓글 갯수", example = "4")
    private Integer childCommentCount;

    @Schema(description = "삭제되었는지", example = "true")
    private Boolean isDeleted;
}