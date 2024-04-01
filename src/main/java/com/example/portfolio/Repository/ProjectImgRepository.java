package com.example.portfolio.Repository;

import com.example.portfolio.Domain.ProjectCategory;
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

    public ProjectImg findProjectImgByProjectImgId (Long projectImgId) {
        ProjectImg projectImg = em.createQuery("SELECT pi FROM ProjectImg pi WHERE pi.id = :projectImgId", ProjectImg.class)
                .setParameter("projectImgId", projectImgId)
                .getSingleResult();
        return projectImg;
    }
}
