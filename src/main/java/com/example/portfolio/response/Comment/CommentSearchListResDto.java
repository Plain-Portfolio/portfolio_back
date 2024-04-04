package com.example.portfolio.response.Comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CommentSearchListResDto {

    private Long projectId;

    @JsonProperty("comments")
    private List<CommentDto> comments;

}
