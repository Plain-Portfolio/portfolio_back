package com.example.portfolio.response.Comment;

import com.example.portfolio.Domain.Comment;
import com.example.portfolio.Domain.Project;
import com.example.portfolio.Domain.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentResponseDto {

    private Long id;
    private String context;
    private Long parentCommentOrderId;
    private Long commentOrder;
    private Integer childCommentCount;
    private Boolean isDeleted;
    private OwnerDto owner;
    private ProjectDto project;

    public CommentResponseDto (Comment comment) {
        this.id = comment.getId();
        this.context = comment.getContext();
        this.parentCommentOrderId = comment.getParentCommentOrderId();
        this.commentOrder = comment.getCommentOrder();
        this.childCommentCount = comment.getChildCommentCount();
        this.isDeleted = comment.getIsDeleted();
        this.owner = new OwnerDto(comment.getUser());
        this.project = new ProjectDto(comment.getProject());
    }

    @Getter
    public static class OwnerDto {
        private Long id;
        private String nickname;
        private String email;

        public OwnerDto(User entity) {

            this.id = entity.getId();
            this.nickname = entity.getNickname();
            this.email = entity.getEmail();
        }
    }

    @Getter
    public static class ProjectDto {
        private Long id;

        public ProjectDto(Project entity) {

            this.id = entity.getId();
        }
    }
}
