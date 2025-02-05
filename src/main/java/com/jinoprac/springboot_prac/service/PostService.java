package com.jinoprac.springboot_prac.service;

import com.jinoprac.springboot_prac.entity.Post;
import com.jinoprac.springboot_prac.exception.PostNotFound;
import com.jinoprac.springboot_prac.repository.post.PostRepository;
import com.jinoprac.springboot_prac.request.PostCreate;
import com.jinoprac.springboot_prac.request.PostEdit;
import com.jinoprac.springboot_prac.request.PostSearch;
import com.jinoprac.springboot_prac.response.PostCreateResponse;
import com.jinoprac.springboot_prac.response.PostEditResponse;
import com.jinoprac.springboot_prac.response.PostGetResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public List<PostGetResponse> getPostList(int page) {
        Pageable pageable = PageRequest.of(page, 100, Sort.by(Sort.Direction.DESC, "createAt"));
        Page<Post> postPage = postRepository.findPostByPage(pageable);

        return postPage.getContent().stream()
                .map(PostGetResponse::from)
                .toList();
    }

    public PostGetResponse getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(PostNotFound::new);

        return PostGetResponse.from(post);
    }

    public List<PostGetResponse> searchPost(PostSearch postSearch) {
        Pageable pageable = PageRequest.of(0, 100, Sort.by(Sort.Direction.DESC, "createAt"));
        Page<Post> postPage = postRepository.findByKeywordContaining(postSearch.keyword(), pageable);

        return postPage.getContent().stream()
                .map(PostGetResponse::from)
                .toList();
    }

    public PostCreateResponse createPost(PostCreate postCreate) {
        Post savedPost = postRepository.save(postCreate.toEntity());
        return PostCreateResponse.from(savedPost);
    }

    public PostEditResponse editPost(Long id, PostEdit postEdit) {
        Post post = postRepository.findById(id).orElseThrow(PostNotFound::new);
        post.edit(postEdit.title(), postEdit.content());
        return PostEditResponse.from(post);
    }

    public void deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(PostNotFound::new);
        postRepository.delete(post);
    }
}