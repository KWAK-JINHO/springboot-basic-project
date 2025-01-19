package com.jinoprac.springboot_prac.service;

import com.jinoprac.springboot_prac.entity.Post;
import com.jinoprac.springboot_prac.repository.post.PostRepository;
import com.jinoprac.springboot_prac.request.PostCreate;
import com.jinoprac.springboot_prac.request.PostEdit;
import com.jinoprac.springboot_prac.response.PostCreateResponse;
import com.jinoprac.springboot_prac.response.PostEditResponse;
import com.jinoprac.springboot_prac.response.PostGetResponse;
import com.jinoprac.springboot_prac.exception.PostNotFound;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        // 왜 매퍼 안썻냐 ㅋ
        // 엔티티로 빼라 ㅋ 메서드 ㅋ
        Post post = Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
//                .createAt(LocalDateTime.now())
                .build();

        Post savedPost = postRepository.save(post);

        return PostCreateResponse.builder()
                .id(savedPost.getId()) // 게시글 작성 후 작성된 페이지로 이동하기 위한 정보
                .build();
    }

    // 게시글 1개 조회
    public PostGetResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);
        // postNotFound라는 메서드를 정의해서 의미있는 오류를 던져준다.

        return PostGetResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createAt(post.getCreateAt())
                .build();
    }

    // 게시글 전체조회 -> 게시글 페이지 조회 로 변경
    public List<PostGetResponse> getAllPosts(int page) {
        Pageable pageable = PageRequest.of(page, 100, Sort.by(Sort.Direction.DESC, "createAt"));

        Page<Post> postPage = postRepository.findAll(pageable);

        List<Post> posts = postPage.getContent();

        List<PostGetResponse> responses = new ArrayList<>(); //  반환해줄 DTO 리스트 생성
        for(Post post : posts) {
            PostGetResponse response = new PostGetResponse(
                    post.getId(),
                    post.getTitle(),
                    post.getContent(),
                    post.getCreateAt()
                    );
            responses.add(response);
        }
        return responses;
    }

    // 게시글 수정
    public PostEditResponse editPost(Long id, PostEdit postEdit) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFound());

        post.edit(postEdit.getTitle(), postEdit.getContent());
        // Post 필드의 값이 변경되었기 때문에 JPA가 변경을 감지해 DB에 반영한다. @Transactional로 인해서 트랜잭션이 커밋되는 시점에 자동으로 UPDATE 쿼리가 발생함
        return PostEditResponse.builder()
                .id(post.getId())
                .build();
    }

    // 게시글 삭제
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFound());
        postRepository.delete(post);
    }
}