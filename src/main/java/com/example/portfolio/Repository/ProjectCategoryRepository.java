package com.example.portfolio.Repository;

import com.example.portfolio.Domain.ProjectCategory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectCategoryRepository {

    @PersistenceContext
    EntityManager em;

    public void save (ProjectCategory projectCategory) {
        em.persist(projectCategory);
    }
}
