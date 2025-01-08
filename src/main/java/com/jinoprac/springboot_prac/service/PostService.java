package com.jinoprac.springboot_prac.service;

import com.jinoprac.springboot_prac.entity.Post;
import com.jinoprac.springboot_prac.repository.post.PostRepository;
import com.jinoprac.springboot_prac.request.PostCreate;
import com.jinoprac.springboot_prac.request.PostEdit;
import com.jinoprac.springboot_prac.response.PostCreateResponse;
import com.jinoprac.springboot_prac.response.PostGetResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // 게시글 작성
    public PostCreateResponse createPost(PostCreate postCreate) {
        Post post = Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .build();

        Post savedPost = postRepository.save(post);

        return PostCreateResponse.builder()
                .id(savedPost.getId()) // 게시글 작성 후 작성된 페이지로 이동하기 위한 정보
                .build();
    }

    // 게시글 1개 조회
    public PostGetResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

        return PostGetResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

    // 게시글 전체 조회
    public List<PostGetResponse> getAllPosts() {
        List<Post> posts = postRepository.findAll(); // 레포의 모든 Post 찾기
        List<PostGetResponse> responses = new ArrayList<>(); //  반환해줄 DTO 리스트 생성

        for(Post post : posts) {
            PostGetResponse response = new PostGetResponse(post.getId(), post.getTitle(), post.getContent());
            responses.add(response);
        }
        return responses;
    }

    // 게시글 수정
    public void editPost(Long id, PostEdit postEdit) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

        post.edit(postEdit.getTitle(), postEdit.getContent());
        // Post 필드의 값이 변경되었기 때문에 JPA가 변경을 감지해 DB에 반영한다. @Transactional로 인해서 트랜잭션이 커밋되는 시점에 자동으로 UPDATE 쿼리가 발생함
    }

    // 게시글 삭제
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

        postRepository.delete(post);
    }
}