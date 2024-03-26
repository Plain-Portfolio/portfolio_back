package com.example.portfolio.Service;

import com.example.portfolio.Domain.Like;
import com.example.portfolio.Domain.Project;
import com.example.portfolio.Domain.User;
import com.example.portfolio.Dto.Like.AddLikeDto;
import com.example.portfolio.Dto.Like.CancelLikeDto;
import com.example.portfolio.Repository.LikeRepository;
import com.example.portfolio.Repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;

@Service
public class LikeService {

    @Autowired
    LikeRepository likeRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Transactional
    public Like addLike (User liker, AddLikeDto addLikeDto) {
        Like like = new Like();
        Project project = projectRepository.findProjectById(addLikeDto.getProjectId());
        like.setUser(liker);
        like.setProject(project);
        likeRepository.save(like);
        return like;
    }

    public void cancelLike (long likerId, CancelLikeDto cancelLikeDto) {
        likeRepository.deleteLikeById(cancelLikeDto.getProjectId());
    }
}
