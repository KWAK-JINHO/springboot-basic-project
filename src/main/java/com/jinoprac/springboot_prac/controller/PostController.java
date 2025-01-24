package com.jinoprac.springboot_prac.controller;

import com.jinoprac.springboot_prac.exception.InvalidRequest;
import com.jinoprac.springboot_prac.exception.PostNotFound;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PostGetResponse> getPostList(@RequestParam(defaultValue = "0") int page) {
        if (page < 0 || page > 999) {
            throw new InvalidRequest("page", "페이지는 0 이상 999 이하만 가능합니다.");
        }
        return postService.getPostList(page);
    }

    @GetMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public PostGetResponse getPost(@PathVariable Long postId) {
        return postService.getPost(postId);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<PostGetResponse> getSearchPosts(@RequestParam(required = false) String keyword) {
        PostSearch request = PostSearch.builder()
                .keyword(keyword)
                .build();
        return postService.searchPost(request);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostCreateResponse createPost(@RequestBody @Valid PostCreate request) {
        return postService.createPost(request);
    }

    @PatchMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public PostEditResponse editPost(@PathVariable Long postId, @RequestBody @Valid PostEdit request) {
        return postService.editPost(postId, request);
    }

    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long postId) {
        postService.deletePost(postId);
    }
}
