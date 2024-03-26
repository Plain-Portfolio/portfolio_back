package com.example.portfolio.Repository;

import com.example.portfolio.Domain.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CategoryRepository {

    @PersistenceContext
    EntityManager em;

    public void save (Category category) {
        em.persist(category);
    }

    public Category findCategoryByCategoryId (Long categoryId) {
        Category category = em.createQuery("SELECT c FROM Category c WHERE c.id = :categoryId", Category.class)
                .setParameter("categoryId", categoryId)
                .getSingleResult();
        return category;
    }

    public void deleteCategoryByCategoryId (Long categoryId) {
        em.createQuery("DELETE FROM Category c WHERE c.id = :categoryId")
                .setParameter("categoryId", categoryId)
                .executeUpdate();
    }

    @Transactional
    public void deleteLikeById (Long likeId) {
        em.createQuery("DELETE FROM Project p WHERE p.id = :likeId")
                .setParameter("likeId", likeId)
                .executeUpdate();
    }
}
