package com.jinoprac.springboot_prac.controller;

import com.jinoprac.springboot_prac.request.PostCreate;
import com.jinoprac.springboot_prac.request.PostEdit;
import com.jinoprac.springboot_prac.response.PostCreateResponse;
import com.jinoprac.springboot_prac.response.PostEditResponse;
import com.jinoprac.springboot_prac.response.PostGetResponse;
import com.jinoprac.springboot_prac.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor // final 인 필드값만 생성자 생성
public class PostController {

    private final PostService postService;

    // 게시글 작성
    @PostMapping("/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public PostCreateResponse createPost(@RequestBody @Valid PostCreate request) {
        return postService.createPost(request);
    }

    // 게시글 1개 조회
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostGetResponse> getPost(@PathVariable Long postId) { //ResponseEntity 사용시 성능차이는 밀리초 단위 이하
        return ResponseEntity.ok(postService.getPost(postId));
    }

    // 게시글 전체 조회
    @GetMapping("/posts/all")
    public ResponseEntity<List<PostGetResponse>> getAllPosts() {
        List<PostGetResponse> responses = postService.getAllPosts();
        return ResponseEntity.ok(responses);
    }

    // 게시글 수정
    @PatchMapping("/posts/{postId}")
    public ResponseEntity<PostEditResponse> editPost(@PathVariable Long postId, @RequestBody @Valid PostEdit request) {
        PostEditResponse updatedPost = postService.editPost(postId, request);
        return ResponseEntity.ok(updatedPost);
    }

    // 게시글 삭제
    @DeleteMapping("/posts/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204: 응답 바디에 콘텐츠 없음
    public void delete(@PathVariable Long postId) {
        postService.deletePost(postId);
    }
}
