package com.example.portfolio.response.Like;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LikeProjectListRes {

    @JsonProperty("likeProjects")
    private List<LikeProjectDto> likeProjects;
}
