package com.example.portfolio.Repository;

import com.example.portfolio.Domain.TeamProjectMember;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class TeamProjectMemberRepository {

    @PersistenceContext
    EntityManager em;

    public void save (TeamProjectMember teamProjectMember) {
        em.persist(teamProjectMember);
    }

//    public TeamProjectM
}
