package com.example.portfolio.Repository;

import com.example.portfolio.Domain.UserImg;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class UserImageRepository {

    @PersistenceContext
    EntityManager em;

    public void save (UserImg userImg) {
        em.persist(userImg);
    }
}
