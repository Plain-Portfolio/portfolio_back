package com.example.portfolio.Repository;

import com.example.portfolio.Common.ErrorCode;
import com.example.portfolio.Domain.ProjectCategory;
import com.example.portfolio.Domain.ProjectImg;
import com.example.portfolio.Exception.Global.UserApplicationException;
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
        System.out.println("???");
        try {
            ProjectImg projectImg = em.createQuery("SELECT pi FROM ProjectImg pi WHERE pi.id = :projectImgId", ProjectImg.class)
                    .setParameter("projectImgId", projectImgId)
                    .getSingleResult();
            return projectImg;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new UserApplicationException(ErrorCode.NO_MATCHING_PROJECTIMG_WITH_PROJECT_IMG_ID);
        }
    }
}
