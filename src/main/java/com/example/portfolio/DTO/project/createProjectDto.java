package com.example.portfolio.Dto.project;

import com.example.portfolio.Domain.ProjectCategory;
import com.example.portfolio.Domain.ProjectImg;
import com.example.portfolio.Domain.TeamProjectMember;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class CreateProjectDto {

    private String title;

    private String description;

    private String githubLink;

    private Boolean isTeamProject;

    private Long ownerId;

    private List<ProjectCategory> projectCategories;

    private List<ProjectImg> projectImgs;

    private List<TeamProjectMember> teamProjectMembers;
}