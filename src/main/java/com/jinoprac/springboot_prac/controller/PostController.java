package com.jinoprac.springboot_prac.controller;

import com.jinoprac.springboot_prac.request.PostCreate;
import com.jinoprac.springboot_prac.response.PostGetResponse;
import com.jinoprac.springboot_prac.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor // final 인 필드값만 생성자 생성
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public void createPost(@RequestBody @Valid PostCreate request) {
        postService.create(request);
    }

//    @GetMapping("/posts/{postId}")
//    public @ResponseBody ResponseEntity<PostReadResponse> readPost(@PathVariable("postId") Long postId) {
//        return ResponseEntity.ok(postService.read(postId));
//    }

    @GetMapping("/posts/{postId}")
    public PostGetResponse readPost(@PathVariable Long postId) {
        return postService.get(postId);
    }
}
