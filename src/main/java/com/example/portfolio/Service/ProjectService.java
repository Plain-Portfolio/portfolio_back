package com.example.portfolio.Service;

import com.example.portfolio.Common.ErrorCode;
import com.example.portfolio.DTO.Project.CreateProjectDto;
import com.example.portfolio.DTO.Project.DeleteProjectDto;
import com.example.portfolio.DTO.Project.UpdateProjectDto;
import com.example.portfolio.Domain.*;
import com.example.portfolio.Exception.Global.UserApplicationException;
import com.example.portfolio.Repository.*;
//import com.example.portfolio.response.Project.CreateProjectResponseDto;
import com.example.portfolio.response.Project.CreateProjectResponseDto;
import com.example.portfolio.response.Project.GetProjectDetailResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    ProjectCategoryRepository projectCategoryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProjectImgRepository projectImgRepository;

    @Autowired
    TeamProjectMemberRepository teamProjectMemberRepository;

    @Autowired
    CategoryRepository categoryRepository;

    public void ValidationCreateProjectDto (CreateProjectDto createProjectDto) {
        if (createProjectDto.getTitle() == null) {
            throw new UserApplicationException(ErrorCode.TITLE_CANNOT_BE_NULL);
        }
        if (createProjectDto.getDescription() == null) {
            throw new UserApplicationException(ErrorCode.DESCRIPTION_CANNOT_BE_NULL);
        }
        if (createProjectDto.getGithubLink() == null) {
            throw new UserApplicationException(ErrorCode.GITHUB_LINK_CANNOT_BE_NULL);
        }
        if (createProjectDto.getIsTeamProject() == null) {
            throw new UserApplicationException(ErrorCode.IS_TEAM_PROJECT_CANNOT_BE_NULL);
        }
//        if (createProjectDto.getOwnerId() == null) {
//            throw new UserApplicationException(ErrorCode.OWNNER_ID_CANNOT_BE_NULL);
//        }

    }

    public void IsSameOwnerIds (Long tokenOwnerId, Long inputOwnerId) {

        if (tokenOwnerId != inputOwnerId) {
            throw new UserApplicationException(ErrorCode.IS_NOT_SAME_OWNERIDS);
        }
    }


    @Transactional
    public CreateProjectResponseDto createProject (User owner, CreateProjectDto createProjectDto) {
        System.out.println("????");
        ValidationCreateProjectDto(createProjectDto);
        Project project = new Project();
        project.setTitle(createProjectDto.getTitle());
        project.setDescription(createProjectDto.getDescription());
        project.setGithubLink(createProjectDto.getGithubLink());
        project.setIsTeamProject(createProjectDto.getIsTeamProject());
        project.setOwner(owner);
        projectRepository.save(project);

        List<ProjectCategory> projectCategoryList = new ArrayList<>();
        if (createProjectDto.getProjectCategories() != null) {
            for (CreateProjectDto.TestProjectCategoryDto projectCategoryDto : createProjectDto.getProjectCategories()) {
                ProjectCategory projectCategory = new ProjectCategory();
                Category findCategory = categoryRepository.findCategoryByCategoryId(projectCategoryDto.getCategoryId());
                projectCategory.setCategory(findCategory);
                projectCategory.setProject(project);
                projectCategoryRepository.save(projectCategory);
                projectCategoryList.add(projectCategory);
            }
        }
        project.setProjectCategories(projectCategoryList);

        List<ProjectImg> projectImgList = new ArrayList<>();
        if (createProjectDto.getProjectImgs() != null) {
            for (CreateProjectDto.TestProjectImgDto projectImgDto : createProjectDto.getProjectImgs()) {
                ProjectImg findProjectImg = projectImgRepository.findProjectImgByProjectImgId(projectImgDto.getId());
                findProjectImg.setProject(project);
                projectImgList.add(findProjectImg);
            }
        }
        project.setProjectImgs(projectImgList);

        List<TeamProjectMember> teamProjectMemberList = new ArrayList<>();
        if (createProjectDto.getTeamProjectMembers() != null) {
            for (CreateProjectDto.TestTeamProjectMemberDto teamProjectMemberDto : createProjectDto.getTeamProjectMembers()) {
                TeamProjectMember teamProjectMember = new TeamProjectMember();
                teamProjectMember.setProject(project);
                User findUser = userRepository.findUserById(teamProjectMemberDto.getUserId());
                System.out.println(findUser);
                System.out.println(findUser.getId());
                System.out.println(findUser.getNickname());
                teamProjectMember.setUser(findUser);
                teamProjectMemberList.add(teamProjectMember);
                teamProjectMemberRepository.save(teamProjectMember);
            }
        }
        project.setTeamProjectMembers(teamProjectMemberList);

        CreateProjectResponseDto response = new CreateProjectResponseDto(project);
        projectRepository.save(project);
        return response;
    }


    public CreateProjectResponseDto getProjectDetail (String projectId) {
        Long parsedProjectId = Long.parseLong(projectId);
        Project project = projectRepository.findProjectById(parsedProjectId);
       CreateProjectResponseDto response = new CreateProjectResponseDto(project);
        return response;
    }

    public List<Project> getProjectList (String userId) {
        List<Project> projects = projectRepository.findProjectByUserId(userId);
        return projects;
    }

    public CreateProjectResponseDto updateProject (UpdateProjectDto updateProjectDto) {
        Project findProject = projectRepository.findProjectById(updateProjectDto.getProjectId());
        findProject.setTitle(updateProjectDto.getTitle());
        findProject.setDescription(updateProjectDto.getDescription());
        findProject.setGithubLink(updateProjectDto.getGithubLink());
        findProject.setIsTeamProject(updateProjectDto.getIsTeamProject());

//        if (updateProjectDto.getProjectCategories() != null) {
//            for (UpdateProjectDto.UpdateProjectCategoryDto updateProjectCategoryDto : updateProjectDto.getProjectCategories()) {
//                ProjectCategory projectCategory = new ProjectCategory();
//                Category findCategory = categoryRepository.findCategoryByCategoryId(projectCategoryDto.getCategoryId());
//                projectCategory.setCategory(findCategory);
//                projectCategory.setProject(project);
//                projectCategoryRepository.save(projectCategory);
//                projectCategoryList.add(projectCategory);
//            }
//        }

        CreateProjectResponseDto response = new CreateProjectResponseDto(findProject);

        return response;
    }

    public void deleteProject (Long projectId, DeleteProjectDto deleteProjectDto) {
        projectRepository.deleleProjectByProjectId(deleteProjectDto.getProjectId());
    }

    public List<Project> categorySearch (List<String> categoryNames) {
        List<Project> projects = projectRepository.projectsSearchByCategories(categoryNames);
        return projects;
    }
}
