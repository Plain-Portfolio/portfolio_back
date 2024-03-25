package com.example.portfolio.Request;

import com.example.portfolio.Domain.Project;
import lombok.Data;

@Data
public class GetProjectListRequest {

    private Project[] project;
}
