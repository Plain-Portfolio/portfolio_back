package com.example.portfolio.response.Like;

import com.example.portfolio.Domain.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
@Data
public class  LikeProjectDto {
    private Long projectId;
    private String title;
    private String description;
    private String githubLink;
    private Boolean isTeamProject;
    private List<ProjectCategoryDto> projectCategories;
    private List<ProjectImgDto> projectImgs;
    private List<LikeDto> likes;
    private List<TeamProjectMemberDto> teamProjectMembers;

    public LikeProjectDto (Project project) {
        this.projectId = project.getId();
        this.title = project.getTitle();
        this.description = project.getDescription();
        this.githubLink = project.getGithubLink();
        this.isTeamProject = project.getIsTeamProject();
        this.projectCategories = project.getProjectCategories().stream()
                .map(projectCategory -> new ProjectCategoryDto(projectCategory))
                .collect(Collectors.toList());
        this.projectImgs = project.getProjectImgs().stream()
                .map(projectImg -> new ProjectImgDto(projectImg))
                .collect(Collectors.toList());
        this.likes = project.getLikes().stream()
                .map(like -> new LikeDto(like))
                .collect(Collectors.toList());
        this.teamProjectMembers = project.getTeamProjectMembers().stream()
                .map(teamProjectMember -> new TeamProjectMemberDto(teamProjectMember))
                .collect(Collectors.toList());
    }
    @Data
    @NoArgsConstructor
    class ProjectCategoryDto {
        private String name;
        private Long id;

        public ProjectCategoryDto(ProjectCategory projectCategory) {
            this.id = projectCategory.getId();
            this.name = projectCategory.getCategory().getName();
        }
    }

    @Data
    @NoArgsConstructor
    public static class ProjectImgDto {
        private Long id;
        private String src;

        public ProjectImgDto(ProjectImg projectImg) {
            this.id = projectImg.getId();
            this.src = projectImg.getImgSrc();
        }
    }

    @Data
    @NoArgsConstructor
    public static class LikeDto {
        private Long likeId;
        private Long userId;

        public LikeDto(Like like) {
            this.likeId = like.getId();
            this.userId = like.getUser().getId();
        }
    }

    @Data
    @NoArgsConstructor
    public static class TeamProjectMemberDto {
        private Long userId;
        private String nickname;

        public TeamProjectMemberDto(TeamProjectMember teamProjectMember) {
            this.userId = teamProjectMember.getId();
            this.nickname = teamProjectMember.getUser().getNickname();
        }
    }
}






