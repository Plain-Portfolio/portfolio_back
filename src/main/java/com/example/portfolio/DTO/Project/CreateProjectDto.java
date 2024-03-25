package com.example.portfolio.DTO.Project;

import com.example.portfolio.Domain.ProjectCategory;
import com.example.portfolio.Domain.ProjectImg;
import com.example.portfolio.Domain.TeamProjectMember;
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

