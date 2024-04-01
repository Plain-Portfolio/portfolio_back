package com.example.portfolio.Domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "projectimgs")
@Getter @Setter
public class ProjectImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "이미지 ID", example = "1")
    private Long id;

    @Column(unique = true)
    @Schema(description = "이미지 src", example = "https://porfolioimage.s3.ap-northeast-2.amazonaws.com/d55b28d1-ffd2-406b-8da0-05acb578031d-%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202024-02-18%20132920.png")
    private String src;

    @Schema(description = "이미지 src", example = "d55b28d1-ffd2-406b-8da0-05acb578031d-%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202024-02-18%20132920.png")
    private String alt;

    @ManyToOne
    private Project project;
}
