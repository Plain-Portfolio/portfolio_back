package com.example.portfolio.response.Image;

import com.example.portfolio.Domain.ProjectImg;
import com.example.portfolio.Domain.UserImg;
import lombok.Data;

@Data
public class UploadImageResponse {

    Long id;
    String alt;
    String imageSrc;

    public UploadImageResponse (ProjectImg projectImg) {
        this.id = projectImg.getId();
        this.alt = projectImg.getAlt();
        this.imageSrc = projectImg.getImgSrc();
    }

    public UploadImageResponse (UserImg userImg) {
        this.id = userImg.getId();
        this.alt = userImg.getAlt();
        this.imageSrc = userImg.getImgSrc();
    }
}
