package com.tenco.blog_v1.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardNativeRepository {
    @Autowired
    private final EntityManager em;

    /**
     * 새로운 게시글 생성
     * @param title
     * @param content
     */
    @Transactional
    public void save(String title, String content) {

        // board_tb 테이블에 제목, 내용, 생성일 을 추가한다.
        Query query = em.createNativeQuery("INSERT INTO board_tb(title, content, created_at) VALUES(?, ?, NOW())" );
        // 쿼리 파라미터 세팅
        query.setParameter(1, title);
        query.setParameter(2, content);
        // 실행
        query.executeUpdate();
    }

    /**
     * 특정 ID의 게시글을 조회 합니다.
     * @param id
     * @return
     */
    public Board findById(int id) {
        // Board.class : 쿼리 결과를 특정 엔티티 객체로 매핑
        Query query = em.createNativeQuery("SELECT * FROM board_tb WHERE id = ?", Board.class);
        query.setParameter(1, id);

        // Query 객체에서 하나의 결과만 반환받고자 할 때 사용하는 메서드입니다.
        // query.getSingleResult()의 타입은 Object 이고, Board에 값을 넣기 위해서는 다운캐스팅이 필요하다.
         Board board = (Board)query.getSingleResult();
        return board;
    }

    /**
     * 모든 게시글 조회
     * @return
     */
    public List<Board> findAll() {
        Query query = em.createNativeQuery("SELECT * FROM board_tb ORDER BY id DESC", Board.class);
        // 2개 이상의 값을 받으려면 getResultList()를 사용해야 한다.
        List<Board> boards = query.getResultList();
        return boards;
    }

    /**
     * 특정 ID로 게시글을 수정하는 기능
     * @param id
     * @param title
     * @param content
     */
    public void updateById(int id, String title, String content) {
        // 게시판 id를 기반으로 게시글 제목, 게시글 내용을 수정한다.
        Query query = em.createNativeQuery("UPDATE board_id SET title = ?, content = ? WHERE id = ?");
        query.setParameter(1,title);
        query.setParameter(2,content);
        query.setParameter(3, id);

        // 쿼리 실행
        query.executeUpdate();
    }

    /**
     * 특정 ID의 게시글을 삭제 한다.
     * @param id
     */
    public void deleteById(int id) {
        Query query = em.createNativeQuery("DELETE FROM board_tb WHERE id = ?");
        query.setParameter(1, id);
        
        // 쿼리 실행
        query.executeUpdate();
    }
}
