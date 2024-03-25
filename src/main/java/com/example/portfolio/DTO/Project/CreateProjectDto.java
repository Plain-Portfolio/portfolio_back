package com.example.portfolio.DTO.Project;

import com.example.portfolio.Domain.ProjectCategory;
import com.example.portfolio.Domain.ProjectImg;
import com.example.portfolio.Domain.TeamProjectMember;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
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

