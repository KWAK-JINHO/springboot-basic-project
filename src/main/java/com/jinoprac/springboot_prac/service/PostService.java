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

    public PostCreateResponse createPost(PostCreate postCreate) {
        Post post = Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .build();
        Post savedPost = postRepository.save(post);
        return PostCreateResponse.builder()
                .id(savedPost.getId())
                .build();
    }

    public PostGetResponse getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(PostNotFound::new);
        return PostGetResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createAt(post.getCreateAt())
                .build();
    }

    public List<PostGetResponse> getPostPage(int page) {
        Pageable pageable = PageRequest.of(page, 100, Sort.by(Sort.Direction.DESC, "createAt"));
        Page<Post> postPage = postRepository.findPostByPage(pageable);

        List<Post> posts = postPage.getContent();
        List<PostGetResponse> responses = new ArrayList<>();
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

    public PostEditResponse editPost(Long id, PostEdit postEdit) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFound());
        post.edit(postEdit.getTitle(), postEdit.getContent());
        return PostEditResponse.builder()
                .id(post.getId())
                .build();
    }

    public void deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFound());
        postRepository.delete(post);
    }

    public List<PostGetResponse> searchPost(PostSearch postSearch) {
        Pageable pageable = PageRequest.of(0, 100, Sort.by(Sort.Direction.DESC, "createAt"));
        Page<Post> postPage = postRepository.findByKeywordContaining(postSearch.getKeyword(), pageable);

        List<Post> posts = postPage.getContent();
        List<PostGetResponse> responses = new ArrayList<>();
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
}