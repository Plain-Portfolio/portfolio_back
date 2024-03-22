package com.example.portfolio.Repository;

import com.example.portfolio.Domain.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryRepository {

    @PersistenceContext
    EntityManager em;

    public void save (Category category) {
        em.persist(category);
    }
}
