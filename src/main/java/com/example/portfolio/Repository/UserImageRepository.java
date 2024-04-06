package com.example.portfolio.Repository;

import com.example.portfolio.Common.ErrorCode;
import com.example.portfolio.Domain.ProjectImg;
import com.example.portfolio.Domain.UserImg;
import com.example.portfolio.Exception.Global.UserApplicationException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserImageRepository {

    @PersistenceContext
    EntityManager em;


    public void save (UserImg userImg) {
        em.persist(userImg);
    }

    public UserImg findUserImgByUserImgId (Long userImgId) {
        System.out.println("???");
        try {
            UserImg userImg = em.createQuery("SELECT ui FROM UserImg ui WHERE ui.id = :userImgId", UserImg.class)
                    .setParameter("userImgId", userImgId)
                    .getSingleResult();
            return userImg;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new UserApplicationException(ErrorCode.NO_MATCHING_USERIMG_WITH_USER_IMG_ID);
        }
    }
}
