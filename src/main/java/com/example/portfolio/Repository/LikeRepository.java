package com.example.portfolio.Repository;

import com.example.portfolio.Domain.Like;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class LikeRepository {

    @PersistenceContext
    EntityManager em;

    public void save (Like like) {
        em.persist(like);
    }

    @Transactional
    public void deleteLikeById (Long likeId) {
        em.createQuery("DELETE FROM Like l WHERE l.id = :likeId")
                .setParameter("likeId", likeId)
                .executeUpdate();
    }

    public Boolean isAlreadyLike (Long likerId, Long projectId) {
        Number likeCount = (Number) em.createQuery("SELECT COUNT(*) FROM Like l WHERE l.user.id = :likerId and l.project.id = :projectId")
                .setParameter("likerId", likerId)
                .setParameter("projectId", projectId)
                .getSingleResult();
        if (likeCount.intValue() > 0) {
            return true;
        }
        return false;
    }
}
