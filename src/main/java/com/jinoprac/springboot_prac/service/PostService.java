package com.jinoprac.springboot_prac.service;

import com.jinoprac.springboot_prac.entity.Post;
import com.jinoprac.springboot_prac.repository.post.PostRepository;
import com.jinoprac.springboot_prac.request.PostCreateRequest;
import com.jinoprac.springboot_prac.response.PostCreateResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // 게시글 작성
    public PostCreateResponse create(PostCreateRequest postCreateRequest) {
        Post post = Post.builder()
                .title(postCreateRequest.getTitle())
                .content(postCreateRequest.getContent())
                .build();

        Post savedPost = postRepository.save(post);
        // 트랜잭션이 없는경우에는 save 이후 즉시 DB에 저장된다.

        return PostCreateResponse.builder()
                .title(savedPost.getTitle())
                .content(savedPost.getContent())
                .build();
    }

    // 게시글 전체 조회 기능
    // 게시글을 조회할 때 `id`, `제목`, `내용`의 값이 포함돼야 한다.
}
