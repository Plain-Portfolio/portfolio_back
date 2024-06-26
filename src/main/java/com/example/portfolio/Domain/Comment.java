package com.example.portfolio.Domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "comments")
@Getter @Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String context;

    private Long parentCommentOrderId;

    private Long commentOrder; // 순서

    private Integer childCommentCount = 0; // 자식 댓글의 개수

    private Boolean isDeleted = false; // 댓글 삭제 여부

    @ManyToOne
    private User user;

    @ManyToOne
    private Project project;
}
