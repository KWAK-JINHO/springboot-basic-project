package com.jinoprac.springboot_prac.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "제목", nullable = false)
    private String title;

    @Column(name = "내용", nullable = false)
    private String content;

    @Column(name = "생성 시간", nullable = false)
    private LocalDateTime createAt;

    @Builder
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @PrePersist
    private void createAt() {
        this.createAt = LocalDateTime.now();
    }

    public void edit(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
