package com.example.portfolio.response.Comment;

import com.example.portfolio.Domain.Comment;
import com.example.portfolio.Domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {

    private Long id;
    private String context;
    private Long parentCommentOrderId;
    private Long commentOrder;
    private Integer childCommentCount;
    private Boolean isDeleted;
    private UserDto userDto;
    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.context = comment.getContext();
        this.parentCommentOrderId = comment.getParentCommentOrderId();
        this.commentOrder = comment.getCommentOrder();
        this.childCommentCount = comment.getChildCommentCount();
        this.isDeleted = comment.getIsDeleted();
        this.userDto = new UserDto(comment.getUser());
    }

    @Getter
    @NoArgsConstructor
    public class UserDto {
        private Long userId;
        private String nickname;

        public UserDto (User user) {
            this.userId = user.getId();
            this.nickname = user.getNickname();
        }
    }
}