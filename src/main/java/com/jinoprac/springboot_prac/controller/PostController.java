package com.jinoprac.springboot_prac.controller;

import com.jinoprac.springboot_prac.request.PostCreate;
import com.jinoprac.springboot_prac.request.PostEdit;
import com.jinoprac.springboot_prac.request.PostSearch;
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
        request.validate();

        return postService.createPost(request);
    }

    // 게시글 1개 조회
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostGetResponse> getPost(@PathVariable Long postId) { //ResponseEntity 사용시 성능차이는 밀리초 단위 이하
        return ResponseEntity.ok(postService.getPost(postId));
    }

    // 게시글 전체 조회 -> 게시글 페이지 조회로 변경
    @GetMapping("/posts")
    public ResponseEntity<List<PostGetResponse>> getPostList(@RequestParam(defaultValue = "0") int page) {
        List<PostGetResponse> responses = postService.getPostList(page);
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

    // 게시글 검색
    @GetMapping("/posts/search")
    public ResponseEntity<List<PostGetResponse>> getSearchPosts(@RequestBody @Valid PostSearch request) {
        List<PostGetResponse> responses = postService.searchPost(request);
        // 검색결과가 없다면 noContent 상태코드 전달
        if (responses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(responses);
    }
}
