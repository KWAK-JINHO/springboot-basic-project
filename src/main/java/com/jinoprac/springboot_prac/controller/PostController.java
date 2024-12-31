package com.jinoprac.springboot_prac.controller;

import com.jinoprac.springboot_prac.request.PostCreateRequest;
import com.jinoprac.springboot_prac.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor // final 인 필드값만 생성자 생성
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public void createPost(@RequestBody @Valid PostCreateRequest request) {
        postService.create(request);
    }
}
