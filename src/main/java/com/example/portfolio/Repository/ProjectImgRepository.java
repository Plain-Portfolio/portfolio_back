package com.example.portfolio.Repository;

import com.example.portfolio.Domain.ProjectImg;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectImgRepository {

    @PersistenceContext
    EntityManager em;

    public void save (ProjectImg projectImg) {
        em.persist(projectImg);
    }
}
