package com.example.portfolio.Repository;

import com.example.portfolio.Domain.ProjectCategory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProjectCategoryRepository {

    @PersistenceContext
    EntityManager em;

    public void save (ProjectCategory projectCategory) {
        em.persist(projectCategory);
    }

    public List<ProjectCategory> findProjectCategoryByProjectId (Long projectId) {
        List<ProjectCategory> projectCategory = em.createQuery("SELECT pc FROM ProjectCategory pc JOIN FETCH pc.project JOIN FETCH pc.category WHERE pc.project.id = :id", ProjectCategory.class)
                .setParameter("id", projectId)
                .getResultList();
        return projectCategory;
    }

    public ProjectCategory findProjectCategoryByProjectCategoryId (Long projectCategoryId) {
        ProjectCategory projectCategory = em.createQuery("SELECT pc FROM ProjectCategory pc WHERE pc.id = :id", ProjectCategory.class)
                .setParameter("id", projectCategoryId)
                .getSingleResult();
        return projectCategory;
    }
}
