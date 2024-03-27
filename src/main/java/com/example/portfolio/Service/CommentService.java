package com.example.portfolio.Service;

import com.example.portfolio.Domain.Comment;
import com.example.portfolio.Domain.Project;
import com.example.portfolio.Domain.User;
import com.example.portfolio.Dto.Comment.CreateCommentDto;
import com.example.portfolio.Dto.Comment.DeleteCommentDto;
import com.example.portfolio.Dto.Comment.UpdateCommentDto;
import com.example.portfolio.Repository.CommentRepository;
import com.example.portfolio.Repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Transactional
    public Comment createComment (User user, CreateCommentDto createCommentDto) {
        Comment comment = new Comment();
        comment.setContext(createCommentDto.getContext());
        comment.setUser(user);
        comment.setParentCommentOrderId(createCommentDto.getParentCommentOrderId());
        comment.setCommentOrder(createCommentDto.getCommentOrder());
        comment.setChildCommentCount(createCommentDto.getChildCommentCount());
        comment.setIsDeleted(createCommentDto.getIsDeleted());
        Project findProject = projectRepository.findProjectById(createCommentDto.getProjectId());
        comment.setProject(findProject);
        commentRepository.save(comment);
        return comment;
    }

    public Comment updateComment (User user, UpdateCommentDto updateCommentDto) {
        Comment comment = commentRepository.findCommentByCommentId(updateCommentDto.getCommentId());
        comment.setContext(updateCommentDto.getContext());
        commentRepository.save(comment);
        return comment;
    }

    public void deleteComment (DeleteCommentDto deleteCommentDto) {
        commentRepository.deleteCommentByCommentId(deleteCommentDto.getCommentId());
    }
}
