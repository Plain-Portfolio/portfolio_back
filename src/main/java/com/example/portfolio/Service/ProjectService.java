package com.example.portfolio.Service;

import com.example.portfolio.Common.ErrorCode;
import com.example.portfolio.Domain.Comment;
import com.example.portfolio.Domain.Project;
import com.example.portfolio.Domain.User;
import com.example.portfolio.Dto.Project.CreateProjectDto;
import com.example.portfolio.Dto.Project.DeleteProjectDto;
import com.example.portfolio.Dto.Project.UpdateProjectDto;
import com.example.portfolio.Exception.Global.UserApplicationException;
import com.example.portfolio.Repository.CommentRepository;
import com.example.portfolio.Repository.ProjectRepository;
import com.example.portfolio.Repository.UserRepository;
import com.example.portfolio.response.Project.GetProjectDetailResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;

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
        if (createProjectDto.getOwnerId() == null) {
            throw new UserApplicationException(ErrorCode.OWNNER_ID_CANNOT_BE_NULL);
        }

    }

    public void IsSameOwnerIds (Long tokenOwnerId, Long inputOwnerId) {

        if (tokenOwnerId != inputOwnerId) {
            throw new UserApplicationException(ErrorCode.IS_NOT_SAME_OWNERIDS);
        }
    }


    @Transactional
    public Project createProject (Long ownerId, CreateProjectDto createProjectDto) throws Exception {
        ValidationCreateProjectDto(createProjectDto);
        IsSameOwnerIds(ownerId, createProjectDto.getOwnerId());
        User findUser = userRepository.findUserById(ownerId);
        System.out.println(findUser + "이거 없는건가?");
        Project project = new Project();
        project.setTitle(createProjectDto.getTitle());
        project.setDescription(createProjectDto.getDescription());
        project.setGithubLink(createProjectDto.getGithubLink());
        project.setIsTeamProject(createProjectDto.getIsTeamProject());
        project.setOwner(findUser);
        projectRepository.save(project);

        return project;
    }


    public GetProjectDetailResponse getProjectDetail (String projectId) {
        Long parsedProjectId = Long.parseLong(projectId);
        Project project = projectRepository.findProjectById(parsedProjectId);
        List<Comment> comments = commentRepository.findCommentsByProjectId(projectId);
        GetProjectDetailResponse response = new GetProjectDetailResponse();
        response.setComment(comments);
        return response;
    }

    public List<Project> getProjectList (String userId) {
        List<Project> projects = projectRepository.findProjectByUserId(userId);
        return projects;
    }

    public Project updateProject (UpdateProjectDto updateProjectDto) {
        Project findProject = projectRepository.findProjectById(updateProjectDto.getId());
        findProject.setTitle(updateProjectDto.getTitle());
        findProject.setDescription(updateProjectDto.getDescription());
        findProject.setGithubLink(updateProjectDto.getGithubLink());
        findProject.setIsTeamProject(updateProjectDto.getIsTeamProject());
        return findProject;
    }

    public void deleteProject (Long projectId, DeleteProjectDto deleteProjectDto) {
        projectRepository.deleleProjectByProjectId(deleteProjectDto.getProjectId());
    }
}
