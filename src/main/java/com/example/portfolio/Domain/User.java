package com.example.portfolio.Domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter @Setter
public class User {

    @Schema(description = "사용자 ID", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @Schema(description = "사용자 email", example = "eos0103@naver.com")
    private String email;

    @Schema(description = "사용자 password", example = "aos4050123!@#")
    private String password;

    @Column(unique = true)
    @Schema(description = "사용자 password", example = "김기범")
    private String nickname;

    @Schema(description = "사용자 본인 소개", example = "저는 2년차 프론트앤드 개발자 김기범입니다. React를 주로 다뤄왔습니다")
    private String Introduction;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Project> projects = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<TeamProjectMember> teamProjectMembers = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<UserImg> userImgs = new ArrayList<>();
}
