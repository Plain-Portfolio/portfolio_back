package com.example.portfolio.Repository;

import com.example.portfolio.Common.ErrorCode;
import com.example.portfolio.Domain.Category;
import com.example.portfolio.Domain.Project;
import com.example.portfolio.Exception.Global.UserApplicationException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class ProjectRepository {

    @PersistenceContext
    EntityManager em;

    public List<Project> findProjectByUserId (String userId) {
        List<Project> projects = em.createQuery("SELECT p FROM Project p WHERE p.owner.id = :userId", Project.class)
                .setParameter("userId", userId)
                .getResultList();
        return projects;
    }

    public Project findProjectById (Long projectId) {
        try {

            Project project = em.createQuery("SELECT p FROM Project p JOIN FETCH p.projectCategories pc JOIN FETCH pc.category WHERE p.id = :id", Project.class)
                    .setParameter("id", projectId)
                    .getSingleResult();
            return project;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new UserApplicationException(ErrorCode.PROJECT_IS_NOT_FOUND);
        }
    }

    @Transactional
    public void deleleProjectByProjectId (Long projectId) {
        em.createQuery("DELETE FROM Project p WHERE p.id = :productId")
                .setParameter("productId", projectId)
                .executeUpdate();
    }

    public void save (Project project) {
        em.persist(project);
    }

    public List<Project> projectsSearchByCategories (List<String> categories) {
        List<String> sqlFilterList = new ArrayList<>();

        List<Project> projects = em.createQuery("SELECT p FROM Project p JOIN p.projectCategories pc WHERE pc.category.name IN (:categoryNames)", Project.class)
                .setParameter("categoryNames", categories)
                .getResultList();
        return projects;
    }
}
