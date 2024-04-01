package com.example.portfolio.DTO.Project;

import com.example.portfolio.Domain.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class UpdateProjectDto {
    private long projectId;
    private String title;
    private String description;
    private String githubLink;
    private Boolean isTeamProject;
    //    private CreateProjectResponseDto.OwnerDto owner;
    private List<UpdateProjectCategoryDto> projectCategories;
    private List<ProjectImgDto> projectImgs;
    //    private List<CommentDto> comments;
//    private List<LikeDto> likes;
    private List<TeamProjectMemberDto> teamProjectMembers;

    public UpdateProjectDto (Project project) {
        this.projectId = project.getId();
        this.title = project.getTitle();
        this.description = project.getDescription();
        this.githubLink = project.getGithubLink();
        this.isTeamProject = project.getIsTeamProject();
//        this.owner = new com.example.portfolio.response.Project.CreateProjectResponseDto.OwnerDto(project.getOwner());
        this.projectCategories = project.getProjectCategories().stream()
                .map(projectCategory -> new TestProjectCategoryDto(projectCategory))
                .collect(Collectors.toList());
        this.projectImgs = project.getProjectImgs().stream()
                .map(projectImg -> new ProjectImgDto(projectImg))
                .collect(Collectors.toList());
//        this.comments = project.getComments().stream()
//                .map(comment -> new CommentDto(comment))
//                .collect(Collectors.toList());
//        this.likes = project.getLikes().stream()
//                .map(like -> new LikeDto(like))
//                .collect(Collectors.toList());
        this.teamProjectMembers = project.getTeamProjectMembers().stream()
                .map(teamProjectMember -> new TeamProjectMemberDto(teamProjectMember))
                .collect(Collectors.toList());
    }

    @Getter
    @NoArgsConstructor
    public static class UpdateProjectCategoryDto {

        private Long categoryId;
        private String CategoryName;

        public UpdateProjectCategoryDto(ProjectCategory projectCategory) {
            this.categoryId = projectCategory.getId();
            this.CategoryName = projectCategory.getCategory().getName();
        }
    }

    //    @Getter
//    @NoArgsConstructor
//    public static class OwnerDto {
//        private Long id;
//
//        public OwnerDto(User entity) {
//            this.id = entity.getId();
//        }
//    }
    @Getter
    @NoArgsConstructor
    public static class ProjectImgDto {
        private Long id;
        private String src;

        public ProjectImgDto(ProjectImg projectImg) {
            this.id = projectImg.getId();
            this.src = projectImg.getSrc();
        }
    }
    @Getter
    @NoArgsConstructor
    public static class CommentDto {
        private Long id;
        private String context;

        public CommentDto(Comment comment) {
            this.id = comment.getId();
            this.context = comment.getContext();
        }
    }
    @Getter
    public class LikeDto {
        private Long id;

        public LikeDto(Like like) {
            this.id = like.getId();
        }
    }
    @Getter
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

