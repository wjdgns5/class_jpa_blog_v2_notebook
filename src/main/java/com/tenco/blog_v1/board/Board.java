package com.tenco.blog_v1.board;

import com.tenco.blog_v1.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "board_tb")
@Data
@NoArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 전략, DB 위임
    private Integer id; // 게시판 Id
    private String title; // 게시판 제목
    private String content; // 게시판 내용

//    @ManyToOne(fetch = FetchType.EAGER)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; // 게시글 작성자 정보

    // created_at 컬럼과 매핑하여, 이 필드는 데이터 저장시 자동으로 설정 됨
    @Column(name = "created_at", insertable = false, updatable = false)
    private Timestamp timestamp; // 작성시간
    // insertable = false : 필드가 데이터베이스에 새로운 레코드를 삽입할 때 자동으로 삽입되지 않음을 의미
    // 만약에 insert 구문에서 value에서 다른 필드값은 다 ?, ?, ? 이렇게 하고 timestamp는 ? 하지 않았다면 값 적용X

    // updatable = false : 필드가 레코드를 업데이트할 때 변경되지 않음을 의미 (수정X)

    @Builder
    public Board(Integer id, String title, String content, User user, Timestamp timestamp) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
        this.timestamp = timestamp;
    }
}
