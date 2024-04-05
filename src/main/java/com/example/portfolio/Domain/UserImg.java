package com.example.portfolio.Domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "userImgs")
public class UserImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @Schema(description = "이미지 src")
    private String imgSrc;

    @Schema(description = "이미지 이름", example = "a0-05acb578031d-%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202024-02-18%20132920.png")
    private String alt;

    @ManyToOne()
    private User owner;
}
