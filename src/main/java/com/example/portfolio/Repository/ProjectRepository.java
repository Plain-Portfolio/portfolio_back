package com.example.portfolio.Repository;

import com.example.portfolio.Domain.Project;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectRepository {

    @PersistenceContext
    EntityManager em;

    public void save (Project project) {
        em.persist(project);
    }
}
