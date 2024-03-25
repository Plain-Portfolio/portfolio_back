package com.example.portfolio.Controller;

import jdk.dynalink.linker.LinkerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class LikeController {

    @Autowired
    LinkerServices likeService;
}
