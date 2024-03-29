package com.example.portfolio.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/image")
public class ImageController {

    @PostMapping(value = "/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {

        return ResponseEntity.ok("success");
    }
}
