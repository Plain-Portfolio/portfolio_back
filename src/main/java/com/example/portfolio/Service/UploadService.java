package com.example.portfolio.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.portfolio.Domain.ProjectImg;
import com.example.portfolio.Domain.UserImg;
import com.example.portfolio.Repository.ProjectImgRepository;
import com.example.portfolio.Repository.UserImageRepository;
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

    @Autowired
    UserImageRepository userImageRepository;


    @Transactional
    public ProjectImg projectUpload(MultipartFile multipartFile) throws IOException {
        String s3FileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();

        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentLength(multipartFile.getInputStream().available());

        amazonS3.putObject(bucket, s3FileName, multipartFile.getInputStream(), objMeta);
        String src = amazonS3.getUrl(bucket, s3FileName).toString();
        ProjectImg projectImg = new ProjectImg();
        projectImg.setImgSrc(src);
        projectImg.setAlt(s3FileName);
        projectImgRepository.save(projectImg);
        return projectImg;
    }

    @Transactional
    public UserImg userUpload(MultipartFile multipartFile) throws IOException {
        String s3FileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();

        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentLength(multipartFile.getInputStream().available());

        amazonS3.putObject(bucket, s3FileName, multipartFile.getInputStream(), objMeta);
        String src = amazonS3.getUrl(bucket, s3FileName).toString();
        UserImg userImg = new UserImg();
        userImg.setImgSrc(src);
        userImg.setAlt(s3FileName);
        userImageRepository.save(userImg);
        return userImg;
    }

}