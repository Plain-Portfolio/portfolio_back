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

    @Schema(description = "ID", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne()
//    @Schema(description = "프로젝트", example = "null")
    private Project project;

//    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;
}
