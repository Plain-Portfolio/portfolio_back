package com.example.portfolio.Domain;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class Like {

    private Long id;

    @ManyToOne
    private User user;
}
