package com.example.portfolio.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.portfolio.Domain.ProjectImg;
import com.example.portfolio.Repository.ProjectImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UploadService {

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

    @Autowired
    ProjectImgRepository projectImgRepository;

    @Transactional
    public ProjectImg upload(MultipartFile multipartFile) throws IOException {
        String s3FileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();

        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentLength(multipartFile.getInputStream().available());

        amazonS3.putObject(bucket, s3FileName, multipartFile.getInputStream(), objMeta);
        String src = amazonS3.getUrl(bucket, s3FileName).toString();
        ProjectImg projectImg = new ProjectImg();
        projectImg.setSrc(src);
        projectImgRepository.save(projectImg);
        return projectImg;
    }

    public String provideImage () {
        return amazonS3.getUrl(bucket, "3553a902-66e2-4bd1-8de0-551b94981953-스크린샷 2024-02-18 132920.png").toString();
    }


}