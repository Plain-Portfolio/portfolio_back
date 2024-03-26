package com.example.portfolio.Repository;

import com.example.portfolio.Domain.Like;
import com.example.portfolio.Dto.Like.CancelLikeDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
}
