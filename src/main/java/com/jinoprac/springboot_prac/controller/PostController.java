package com.jinoprac.springboot_prac.controller;

import com.jinoprac.springboot_prac.config.data.UserSession;
import com.jinoprac.springboot_prac.request.PostCreate;
import com.jinoprac.springboot_prac.request.PostEdit;
import com.jinoprac.springboot_prac.request.PostSearch;
import com.jinoprac.springboot_prac.response.PostCreateResponse;
import com.jinoprac.springboot_prac.response.PostEditResponse;
import com.jinoprac.springboot_prac.response.PostGetResponse;
import com.jinoprac.springboot_prac.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @GetMapping("/foo")
    public String foo(UserSession userSession) {
        log.info(">>>{}", userSession.name);
        return userSession.name;
    }

    @GetMapping
    public List<PostGetResponse> getPostList(@RequestParam(defaultValue = "0") int page) {
        if (page < 0 || page > 999) {
            page = 0;
        }
        return postService.getPostList(page);
    }

    @GetMapping("/{postId}")
    public PostGetResponse getPost(@PathVariable Long postId) {
        return postService.getPost(postId);
    }

    @GetMapping("/search")
    public List<PostGetResponse> getSearchPosts(@RequestParam(required = false) String keyword) {
        PostSearch request = new PostSearch(keyword);
        return postService.searchPost(request);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostCreateResponse createPost(@RequestBody @Valid PostCreate request) {
        return postService.createPost(request);
    }

    @PatchMapping("/{postId}")
    public PostEditResponse editPost(@PathVariable Long postId, @RequestBody @Valid PostEdit request) {
        return postService.editPost(postId, request);
    }

    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long postId) {
        postService.deletePost(postId);
    }
}
