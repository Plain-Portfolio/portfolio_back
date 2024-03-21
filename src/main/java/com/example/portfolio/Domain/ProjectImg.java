package com.example.portfolio.Domain;

import jakarta.persistence.*;

@Entity
public class ProjectImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Project project;
}
