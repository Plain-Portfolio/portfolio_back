package com.example.portfolio.Domain;

import jakarta.persistence.*;

@Entity
public class ProjectCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Project project;

    @ManyToOne
    private Category category;
}
