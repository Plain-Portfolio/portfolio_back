package com.example.portfolio.DTO.Project;

import com.example.portfolio.Domain.ProjectCategory;
import com.example.portfolio.Domain.ProjectImg;
import com.example.portfolio.Domain.TeamProjectMember;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class CreateProjectDto {

    @Schema(description = "프로젝트 제목", example = "온라인 쇼핑몰 구현")
    private String title;

    @Schema(description = "프로젝트 설명", example = "React를 이용한 알림, 대댓글, 결제가 구현된 쇼핑몰 서비스")
    private String description;

    @Schema(description = "프로젝트 깃허브", example = "https://github.com/tempTeam1213/portfolio_front")
    private String githubLink;

    @Schema(description = "팀프로젝트가 맞는지 판별", example = "false")
    private Boolean isTeamProject;

    private Long ownerId;

    private List<ProjectCategory> projectCategories;

    private List<ProjectImg> projectImgs;

    private List<TeamProjectMember> teamProjectMembers;
}

