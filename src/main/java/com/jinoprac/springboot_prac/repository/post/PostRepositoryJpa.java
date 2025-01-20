package com.jinoprac.springboot_prac.repository.post;

import com.jinoprac.springboot_prac.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepositoryJpa extends JpaRepository<Post, Long> {

    // 게시글 페이지 조회
    Page<Post> findAll(Pageable pageable);

    // 게시글 제목으로 검색
    Page<Post> findByTitleContains(String title, Pageable pageable);
}
