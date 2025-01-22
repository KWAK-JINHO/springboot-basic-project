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
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/posts")
    @ResponseStatus(HttpStatus.OK)
    public List<PostGetResponse> getPostList(@RequestParam(defaultValue = "0") int page) {
        return postService.getPostList(page);
    }

    @GetMapping("/posts/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public PostGetResponse getPost(@PathVariable Long postId) {
        return postService.getPost(postId);
    }

    @GetMapping("/posts/search")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<PostGetResponse>> getSearchPosts(@RequestParam(required = false) String keyword) {
        PostSearch request = PostSearch.builder()
                .keyword(keyword)
                .build();
        List<PostGetResponse> responses = postService.searchPost(request);
        return ResponseEntity.ok(responses);
    }

    @PostMapping("/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public PostCreateResponse createPost(@RequestBody @Valid PostCreate request) {
        return postService.createPost(request);
    }

    @PatchMapping("/posts/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public PostEditResponse editPost(@PathVariable Long postId, @RequestBody @Valid PostEdit request) {
        return postService.editPost(postId, request);
    }

    @DeleteMapping("/posts/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long postId) {
        postService.deletePost(postId);
    }
}
