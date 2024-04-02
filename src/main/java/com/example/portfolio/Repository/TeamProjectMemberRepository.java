package com.example.portfolio.Repository;

import com.example.portfolio.Domain.TeamProjectMember;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class TeamProjectMemberRepository {

    @PersistenceContext
    EntityManager em;

    @Transactional
    public void deleteTeamProjectMemberByProjectId (Long projectId) {
        em.createQuery("DELETE FROM TeamProjectMember pm WHERE pm.project.id = :projectId")
                .setParameter("projectId", projectId)
                .executeUpdate();
    }

    public void save (TeamProjectMember teamProjectMember) {
        em.persist(teamProjectMember);
    }

//    public TeamProjectM
}
