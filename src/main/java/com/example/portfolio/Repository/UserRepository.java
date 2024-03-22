package com.example.portfolio.Repository;

import com.example.portfolio.Domain.Category;
import com.example.portfolio.Domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    @PersistenceContext
    EntityManager em;

    public void save (User user) {
        em.persist(user);
    }
}
