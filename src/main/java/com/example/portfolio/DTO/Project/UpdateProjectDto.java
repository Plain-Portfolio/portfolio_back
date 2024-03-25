package com.example.portfolio.Dto.project;

import com.example.portfolio.Domain.ProjectCategory;
import com.example.portfolio.Domain.ProjectImg;
import com.example.portfolio.Domain.TeamProjectMember;
import lombok.Data;

import java.util.List;

@Data
public class UpdateProjectDto {
    private Long id;

    private String title;

    private String description;

    private String githubLink;

    private Boolean isTeamProject;

    private List<ProjectCategory> projectCategories;

    private List<ProjectImg> projectImgs;

    private List<TeamProjectMember> teamProjectMembers;
}