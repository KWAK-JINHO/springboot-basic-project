package com.jinoprac.springboot_prac.repository.post;

import com.jinoprac.springboot_prac.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends PostRepositoryJpa {
    // PostRepository는 진입정의 역할만을 한다(책임의 분리)
}
