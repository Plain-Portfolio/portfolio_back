package com.example.portfolio.response.Project;

import com.example.portfolio.Domain.Comment;
import com.example.portfolio.Domain.Project;
import lombok.Data;

import java.util.List;


@Data
public class GetProjectDetailResponse {

    private Project project;
    private List<Comment> comment;
}
