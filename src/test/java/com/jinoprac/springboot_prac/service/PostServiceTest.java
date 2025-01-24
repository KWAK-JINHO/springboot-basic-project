package com.jinoprac.springboot_prac.service;

import com.jinoprac.springboot_prac.entity.Post;
import com.jinoprac.springboot_prac.exception.PostNotFound;
import com.jinoprac.springboot_prac.repository.post.PostRepository;
import com.jinoprac.springboot_prac.request.PostCreate;
import com.jinoprac.springboot_prac.request.PostEdit;
import com.jinoprac.springboot_prac.response.PostGetResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성 테스트 입니다.")
    void 게시글_작성_테스트() {
        // given
        PostCreate postCreate = new PostCreate("제목입니다", "내용입니다");

        // when
        postService.createPost(postCreate);

        // then
        Post post = postRepository.findAll().get(0);

        assertEquals(1L, postRepository.count(), "게시글 수가 1이 아닙니다.");
        assertEquals("제목입니다", post.getTitle(), "제목이 일치하지 않습니다.");
        assertEquals("내용입니다", post.getContent(), "내용이 일치하지 않습니다.");
        assertNotNull(post.getId(), "ID가 null입니다.");
    }

    @Test
    @DisplayName("글 1개 조회 테스트 입니다.")
    void 게시글_1개조회_테스트() {
        //given
        Post post = Post.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();
        postRepository.save(post);

        // when
        PostGetResponse postGetResponse = postService.getPost(post.getId());

        // then
        assertNotNull(postGetResponse);
        assertEquals("제목입니다.", postGetResponse.title());
        assertEquals("내용입니다.", postGetResponse.content());
    }

    @Test
    @DisplayName("글 1개 조회 테스트 입니다.")
    void 게시글_1개조회_실패_테스트() {
        //given
        Post post = Post.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();
        postRepository.save(post);

        // expected
        assertThrows(PostNotFound.class, () -> {
            postService.getPost(post.getId() + 1L);
        });
    }

    @Test
    @DisplayName("글 여러개 조회 테스트 입니다.")
    void 게시글_전체조회_테스트() {
        // given
        List<Post> posts = List.of(
                Post.builder().title("제목1").content("내용1").build(),
                Post.builder().title("제목2").content("내용2").build(),
                Post.builder().title("제목3").content("내용3").build()
        );
        postRepository.saveAll(posts);

        // when
        List<PostGetResponse> responses = postService.getPostList(0);

        // then
        assertEquals(3, responses.size());
        assertEquals("제목1", responses.get(2).title());
        assertEquals("제목2", responses.get(1).title());
        assertEquals("제목3", responses.get(0).title());
    }

    @Test
    @DisplayName("게시글 페이지 조회시 100개씩 가지고 오나요?")
    void 게시글_전체_조회시_100개씩_불러오기_테스트() {
        // given
        List<Post> requestPosts = IntStream.range(1, 201)
                .mapToObj(i -> Post.builder()
                        .title("제목 " + i)
                        .content("내용 " + i)
                        .build())
                .collect(Collectors.toList());
        postRepository.saveAll(requestPosts);

        // when
        List<PostGetResponse> posts = postService.getPostList(0);

        // then
        assertEquals(100L, posts.size());
        assertEquals("제목 200", posts.get(0).title());
        assertEquals("제목 101", posts.get(99).title());
    }

    @Test
    @DisplayName("게시글 제목수정 테스트입니다.")
    void 게시글_제목수정_테스트() {
        //given
        Post post = Post.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();
        postRepository.save(post);

        PostEdit postEdit = new PostEdit("수정된 제목입니다.", "내용입니다.");

        // when
        postService.editPost(post.getId(), postEdit);

        // then
        Post changePost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재 하지 않습니다. id" + post.getId()));
        assertEquals("수정된 제목입니다.", changePost.getTitle());
    }

    @Test
    @DisplayName("게시글 제목수정 - 게시글이 존재 하지 않을 때.")
    void 글이_존재하지_않을_때_게시글_제목수정_테스트입니다() {
        //given
        Post post = Post.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();
        postRepository.save(post);

        PostEdit postEdit = new PostEdit("수정된 제목입니다.", "내용입니다.");

        // expected
        assertThrows(PostNotFound.class, () -> {
            postService.editPost(post.getId() + 1L, postEdit);
        });
    }

    @Test
    @DisplayName("게시글 삭제 테스트입니다")
    void 게시글이_잘지워지나요() {
        // given
        Post post = Post.builder()
                .title("제목입니다")
                .content("내용입니다")
                .build();
        postRepository.save(post);

        // when
        postService.deletePost(post.getId());

        // then
        assertEquals(0, postRepository.count());
    }

    @Test
    @DisplayName("게시글 삭제 - 존재하지 않는 글")
    void 게시글이_존재하지_않을_때_삭제시도() {
        // given
        Post post = Post.builder()
                .title("제목입니다")
                .content("내용입니다")
                .build();
        postRepository.save(post);

        // expected
        assertThrows(PostNotFound.class, () -> {
            postService.deletePost(post.getId() + 1L);
        });
    }
}