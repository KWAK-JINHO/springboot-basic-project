package com.jinoprac.springboot_prac.repository.post;

import com.jinoprac.springboot_prac.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepositoryJpa extends JpaRepository<Post, Long> {

    // 게시글 페이지 조회
    Page<Post> findAll(Pageable pageable);

    // 게시글 키워드 검색 (row는 유일한 데이터를 반환하기 때문에 중복되지 않음)
    @Query("SELECT p FROM Post p WHERE p.title LIKE %:keyword% OR p.content LIKE %:keyword% ORDER BY p.createAt DESC")
    Page<Post> findByKeywordContaining(@Param("keyword") String keyword, Pageable pageable);
}
