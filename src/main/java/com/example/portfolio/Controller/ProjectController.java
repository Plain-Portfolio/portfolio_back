package com.example.portfolio.Controller;

import com.example.portfolio.Domain.Project;
import com.example.portfolio.Service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProjectController {

    @Autowired
    ProjectService projectService;
}
