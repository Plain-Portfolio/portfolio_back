package com.example.portfolio.Repository;

import com.example.portfolio.Common.ErrorCode;
import com.example.portfolio.Domain.Category;
import com.example.portfolio.Domain.Project;
import com.example.portfolio.Domain.ProjectCategory;
import com.example.portfolio.Exception.Global.UserApplicationException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class ProjectRepository {

    @PersistenceContext
    EntityManager em;

    @Autowired
    LikeRepository likeRepository;

    @Autowired
    ProjectCategoryRepository projectCategoryRepository;

    @Autowired
    TeamProjectMemberRepository teamProjectMemberRepository;

    @Transactional
    public void deleteEntityAndRelatedData (Long projectId) {
        likeRepository.deleteLikeById(projectId);
        projectCategoryRepository.deleteProjectCategoryByProjectId(projectId);
        teamProjectMemberRepository.deleteTeamProjectMemberByProjectId(projectId);
    }

    public Long countProjectByTitle (String title) {
        Long count = em.createQuery("SELECT COUNT(e) FROM Project e WHERE e.title = :title", Long.class)
                .setParameter("title", title)
                .getSingleResult();
        return count;
    }

    public List<Project> findProjectsByUserId (String userId) {
        try {
            List<Project> projects = em.createQuery("SELECT p FROM Project p WHERE p.owner.id = :userId", Project.class)
                    .setParameter("userId", userId)
                    .getResultList();
            return projects;
        } catch (Exception ex) {
            throw new UserApplicationException(ErrorCode.INVALID_USERID_WAS_PROVIDED);
        }
    }

    public Project findProjectById (Long projectId) {
        try {

            Project project = em.createQuery("SELECT p FROM Project p WHERE p.id = :id", Project.class)
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
        try {
            List<Project> projects = em.createQuery("SELECT p FROM Project p JOIN p.projectCategories pc WHERE pc.category.name IN (:categoryNames)", Project.class)
                    .setParameter("categoryNames", categories)
                    .getResultList();
            return projects;
        } catch (Exception ex) {
            throw new UserApplicationException(ErrorCode.CATEGORIES_IS_VALID);
        }
    }
}
