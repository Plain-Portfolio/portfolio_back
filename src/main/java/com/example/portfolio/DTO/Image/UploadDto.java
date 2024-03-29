package com.example.portfolio.DTO.Image;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadDto {
    private MultipartFile file;
}
