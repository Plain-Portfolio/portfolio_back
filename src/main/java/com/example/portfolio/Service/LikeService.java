package com.example.portfolio.Service;

import com.example.portfolio.Common.ErrorCode;
import com.example.portfolio.DTO.Like.AddLikeDto;
import com.example.portfolio.DTO.Like.CancelLikeDto;
import com.example.portfolio.Domain.Like;
import com.example.portfolio.Domain.Project;
import com.example.portfolio.Domain.User;
import com.example.portfolio.Exception.Global.UserApplicationException;
import com.example.portfolio.Repository.LikeRepository;
import com.example.portfolio.Repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@Service
public class LikeService {

    @Autowired
    LikeRepository likeRepository;

    @Autowired
    ProjectRepository projectRepository;

    public List<Project> likeProjectList (User user) {
        List<Project> projects = projectRepository.findProjectsByLikerId(user.getId().toString());
        return projects;
    }

    @Transactional
    public Like addLike (User liker, AddLikeDto addLikeDto) {
        Like like = new Like();
        // 프로젝트 조회
        Project project = projectRepository.findProjectById(addLikeDto.getProjectId());

        // 이미 좋아요했는지 조회
        Boolean isLiked = likeRepository.isAlreadyLike(liker.getId(), project.getId());
        if (isLiked == true) {
            throw new UserApplicationException(ErrorCode.ALREADY_LIKED);
        }

        like.setUser(liker);
        like.setProject(project);
        likeRepository.save(like);
        return like;
    }

    public void cancelLike (long likerId, CancelLikeDto cancelLikeDto) {
        boolean isliked = likeRepository.isAlreadyLike(likerId, cancelLikeDto.getProjectId());
        if (isliked == false) {
            throw new UserApplicationException(ErrorCode.PROJECT_NOT_YEY_LIKED);
        }
        likeRepository.deleteLikeById(cancelLikeDto.getProjectId());
    }
}
