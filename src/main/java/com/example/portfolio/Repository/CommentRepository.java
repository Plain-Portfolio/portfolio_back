package com.example.portfolio.Repository;

import com.example.portfolio.Domain.Comment;
import com.example.portfolio.Dto.Comment.CreateCommentDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentRepository {

    @PersistenceContext
    EntityManager em;

    public void save (Comment comment) {
        em.persist(comment);
    }

    public List<Comment> findCommentsByProjectId(String projectId) {
        List<Comment> comments = em.createQuery("SELECT c FROM Comment c WHERE c.project.id = :projectId", Comment.class)
                .setParameter("projectId", projectId)
                .getResultList();
        return comments;
    }
}
