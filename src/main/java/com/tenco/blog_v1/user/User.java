package com.tenco.blog_v1.user;

import com.tenco.blog_v1.board.Board;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "user_tb")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(updatable = false) // 업데이트 제약조건 설정
    private String username;
    private String password;
    private String email;

    @CreationTimestamp // 엔티티(Entity) 생성시 자동으로 현재 시간 입력 어노테이션
    private Timestamp createdAt;

 // @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    // 사용자 한명은 게시글을 여러개 작성할 수 있다.
    private List<Board> boards;

    @Builder
    public User(Integer id, String username, String password, String email, Timestamp createdAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.createdAt = createdAt;
    }
}
