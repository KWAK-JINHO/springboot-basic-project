package com.jinoprac.springboot_prac.controller;

import com.jinoprac.springboot_prac.request.PostCreate;
import com.jinoprac.springboot_prac.request.PostEdit;
import com.jinoprac.springboot_prac.response.PostCreateResponse;
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
    public ResponseEntity<PostCreateResponse> createPost(@RequestBody @Valid PostCreate request) {
        PostCreateResponse response = postService.createPost(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

//    @GetMapping("/posts/{postId}")
//    public @ResponseBody ResponseEntity<PostReadResponse> readPost(@PathVariable("postId") Long postId) {
//        return ResponseEntity.ok(postService.read(postId));
//    }

    // 게시글 1개 조회
    @GetMapping("/posts/{postId}")
    public PostGetResponse getPost(@PathVariable Long postId) {
        return postService.getPost(postId);
    }

    // 게시글 전체 조회
    @GetMapping("/posts/all")
    public List<PostGetResponse> getAllPosts() {
        return postService.getAllPosts();
    }

    // 게시글 수정
    @PatchMapping("/posts/{postId}")
    public void editPost(@PathVariable Long postId, @RequestBody @Valid PostEdit request) {
        postService.editPost(postId, request);
    }
}
