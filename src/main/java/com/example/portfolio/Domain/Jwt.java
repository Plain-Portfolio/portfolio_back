package com.example.portfolio.Domain;

import jakarta.persistence.Id;

public class Jwt {

    @Id
    private String id;
    private String payload;
}
