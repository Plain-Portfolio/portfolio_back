package com.example.portfolio.Domain;

import jakarta.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String context;

    @ManyToOne
    private User user;

    @ManyToOne
    private Project project;

}
