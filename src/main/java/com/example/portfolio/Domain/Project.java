package com.example.portfolio.Domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projects")
@Getter @Setter
public class Project {

    @Schema(description = "프로젝트 ID", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "프로젝트 제목", example = "온라인 쇼핑몰 구현")
    @Column(unique = true)
    private String title;

    @Schema(description = "프로젝트 설명", example = "React를 이용한 알림, 대댓글, 결제가 구현된 쇼핑몰 서비스")
    private String description;

    @Schema(description = "프로젝트 깃허브", example = "https://github.com/tempTeam1213/portfolio_front")
    private String githubLink;

    @Schema(description = "팀프로젝트가 맞는지 판별", example = "false")
    private Boolean isTeamProject;

    @ManyToOne
    @Schema(description = "소유자")
    private User owner;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProjectCategory> projectCategories = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProjectImg> projectImgs = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Like> likes = new ArrayList<>();
//
//    @JsonManagedReference
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TeamProjectMember> teamProjectMembers = new ArrayList<>();
}
