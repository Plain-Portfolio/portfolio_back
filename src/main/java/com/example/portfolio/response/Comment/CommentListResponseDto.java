package com.example.portfolio.response.Comment;

import com.example.portfolio.response.Project.CreateProjectResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CommentListResponseDto {

    @JsonProperty("comments")
    private List<CommentResponseDto> comments;

}
