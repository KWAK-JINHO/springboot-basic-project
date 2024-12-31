package com.jinoprac.springboot_prac.repository.post;

import com.jinoprac.springboot_prac.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepositoryJpa extends JpaRepository<Post, Long> {
    // 기본적인 CRUD는 JpaRepository에서 제공
    // 추가적으로 필요한 CRUD 메서드는 여기서
}
