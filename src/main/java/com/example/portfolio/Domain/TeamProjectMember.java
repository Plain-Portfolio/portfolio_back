package com.example.portfolio.Domain;

import jakarta.persistence.*;

@Entity
public class TeamProjectMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Project project;
}
