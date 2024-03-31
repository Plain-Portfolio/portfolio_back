package com.example.portfolio.Domain;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "projectcategories")
@Getter @Setter
public class ProjectCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;


    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;
}
