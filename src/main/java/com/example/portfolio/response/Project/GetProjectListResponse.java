package com.example.portfolio.response.Project;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetProjectListResponse {

    @JsonProperty("projects")
    private List<GetProjectRes> projects;


}
