package com.tenco.blog_v1.board;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BoardRepository {

    @Autowired
    private final EntityManager em;

    /**
     * 게시글 조회 메서드
     * @param id
     * @return
     */
    public Board findById(int id) {
        Board board = em.find(Board.class, id);
        return board;
    }

    public Board findByIdJoinUser(int id) {

        // JPQL --> Fetch join을 사용해 보자.
        String jpql = " SELECT * FROM Board b JOIN FETCH b,.user WHERE b.id = :id ";
        return em.createQuery(jpql, Board.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}
