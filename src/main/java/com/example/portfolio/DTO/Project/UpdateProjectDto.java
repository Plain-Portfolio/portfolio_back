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
    private List<UpdateProjectCategoryDto> projectCategories;
    private List<UpdateProjectImgDto> projectImgs;
    private List<UpdateProjectMemberDto> teamProjectMembers;

    public UpdateProjectDto (Project project) {
        this.projectId = project.getId();
        this.title = project.getTitle();
        this.description = project.getDescription();
        this.githubLink = project.getGithubLink();
        this.isTeamProject = project.getIsTeamProject();
//        this.owner = new com.example.portfolio.response.Project.CreateProjectResponseDto.OwnerDto(project.getOwner());
        this.projectCategories = project.getProjectCategories().stream()
                .map(projectCategory -> new UpdateProjectCategoryDto(projectCategory))
                .collect(Collectors.toList());
        this.projectImgs = project.getProjectImgs().stream()
                .map(projectImg -> new UpdateProjectImgDto(projectImg))
                .collect(Collectors.toList());
        this.teamProjectMembers = project.getTeamProjectMembers().stream()
                .map(teamProjectMember -> new UpdateProjectMemberDto(teamProjectMember))
                .collect(Collectors.toList());
    }

    @Getter
    @NoArgsConstructor
    public static class UpdateProjectCategoryDto {

        private Long categoryId;
//        private String CategoryName;

        public UpdateProjectCategoryDto(ProjectCategory projectCategory) {
            this.categoryId = projectCategory.getId();
//            this.CategoryName = projectCategory.getCategory().getName();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class UpdateProjectImgDto {
        private Long id;
        private String src;

        public UpdateProjectImgDto(ProjectImg projectImg) {
            this.id = projectImg.getId();
            this.src = projectImg.getSrc();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class UpdateProjectMemberDto {
        private Long userId;
        private String nickname;

        public UpdateProjectMemberDto(TeamProjectMember teamProjectMember) {
            this.userId = teamProjectMember.getId();
            this.nickname = teamProjectMember.getUser().getNickname();
        }
    }
}

