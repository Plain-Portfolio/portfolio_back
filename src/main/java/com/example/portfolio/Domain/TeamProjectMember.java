package com.example.portfolio.Domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "teamprojectmembers")
@Getter @Setter
public class TeamProjectMember {

    @Id
    @Schema(description = "ID", example = "1")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
//    @Schema(description = "프로젝트", example = "null")
    @JsonBackReference
    private Project project;
}
