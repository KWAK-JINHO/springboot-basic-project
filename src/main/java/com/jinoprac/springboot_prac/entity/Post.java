package com.jinoprac.springboot_prac.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto_Increment
    private Long id;

    @Column(name = "제목", nullable = false)
    private String title;

    @Column(name = "내용", nullable = false)
    private String content;
}
