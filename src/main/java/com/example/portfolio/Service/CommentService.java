package com.example.portfolio.Service;

import com.example.portfolio.Common.ErrorCode;
import com.example.portfolio.DTO.Comment.CreateCommentDto;
import com.example.portfolio.DTO.Comment.DeleteCommentDto;
import com.example.portfolio.DTO.Comment.UpdateCommentDto;
import com.example.portfolio.Domain.Comment;
import com.example.portfolio.Domain.Project;
import com.example.portfolio.Domain.User;
import com.example.portfolio.Exception.Global.UserApplicationException;
import com.example.portfolio.Repository.CommentRepository;
import com.example.portfolio.Repository.ProjectRepository;
import com.example.portfolio.response.Comment.CommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    ProjectRepository projectRepository;

    public List<Comment> searchComments (Long projectId) {
        projectRepository.findProjectById(projectId);
        List<Comment> comments = commentRepository.findCommentsByProjectId(projectId);
        return comments;
    }

    @Transactional
    public Comment createComment (User user, CreateCommentDto createCommentDto) {
        Comment comment = new Comment();
        comment.setContext(createCommentDto.getContext());
        comment.setUser(user);

        if (createCommentDto.getParentCommentOrderId() != null) {
            Long checkParentCommentCount = commentRepository.countCommentsByCommentId(createCommentDto.getParentCommentOrderId());

            if (checkParentCommentCount != 1) {
                throw new UserApplicationException(ErrorCode.NO_MATCHING_PARENTCOMMENT_FOUND_WITH_COMMENTID);
            }
            Comment parentComment = commentRepository.findCommentByCommentId(createCommentDto.getParentCommentOrderId());
            parentComment.setChildCommentCount(parentComment.getChildCommentCount() + 1);
            commentRepository.save(parentComment);
        }

        comment.setParentCommentOrderId(createCommentDto.getParentCommentOrderId());
        comment.setCommentOrder(createCommentDto.getCommentOrder());
        comment.setChildCommentCount(createCommentDto.getChildCommentCount());
        comment.setIsDeleted(createCommentDto.getIsDeleted());
        Project findProject = projectRepository.findProjectById(createCommentDto.getProjectId());
        comment.setProject(findProject);
        commentRepository.save(comment);
        return comment;
    }

    @Transactional
    public Comment updateComment (User user, UpdateCommentDto updateCommentDto) {
        Comment comment = commentRepository.findCommentByCommentId(updateCommentDto.getCommentId());
        comment.setContext(updateCommentDto.getContext());
        comment.setCommentOrder(updateCommentDto.getCommentOrder());
        comment.setParentCommentOrderId(updateCommentDto.getParentCommentOrderId());
        comment.setChildCommentCount(updateCommentDto.getChildCommentCount());
        commentRepository.save(comment);
        return comment;
    }

    public void deleteComment (DeleteCommentDto deleteCommentDto) {
        commentRepository.deleteCommentByCommentId(deleteCommentDto.getCommentId());
    }

    public List<Comment> findCommentList () {
        List<Comment> comments = commentRepository.findAllComment();
        return comments;
    }
}
