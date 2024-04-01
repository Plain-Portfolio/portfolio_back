package com.example.portfolio.Domain;

import com.fasterxml.jackson.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "projectcategories")
@Getter @Setter
public class ProjectCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "프로젝트카테고리 ID", example = "1")
    private Long id;

    @ManyToOne()
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;
}
