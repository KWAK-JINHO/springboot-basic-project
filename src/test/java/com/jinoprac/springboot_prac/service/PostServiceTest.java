package com.jinoprac.springboot_prac.service;

import com.jinoprac.springboot_prac.entity.Post;
import com.jinoprac.springboot_prac.repository.post.PostRepository;
import com.jinoprac.springboot_prac.request.PostCreate;
import com.jinoprac.springboot_prac.request.PostEdit;
import com.jinoprac.springboot_prac.response.PostGetResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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
        PostCreate postCreate = PostCreate.builder()
                .title("제목입니다")
                .content("내용입니다")
                .build();

        // when
        postService.createPost(postCreate);

        // then
        assertEquals(1L, postRepository.count());
        Post post = postRepository.findAll().get(0);
        assertEquals("제목입니다", post.getTitle());
        assertEquals("내용입니다", post.getContent());
        assertNotNull(post.getId());
        System.out.println("ID는" + post.getId() + "입니다.");
    }

    @Test
    @DisplayName("글 1개 조회 테스트 입니다.")
    void 게시글_1개조회_테스트() {
        //given
        Post requestPost = Post.builder() // Post엔티티 객체를 만들어서 title,과 content를 DB에 저장 (id는 GeneratedValue에 의해 자동생성)
                .title("제목입니다.")
                .content("내용입니다.")
                .build();
        postRepository.save(requestPost);

        // when
        PostGetResponse postGetResponse = postService.getPost(requestPost.getId()); // PostReadResponse 객체에 위에 저장한 엔티티의 id를 할당

        // then
        assertNotNull(postGetResponse);
        assertEquals("제목입니다.", postGetResponse.getTitle());
        assertEquals("내용입니다.", postGetResponse.getContent());
    }

    @Test
    @DisplayName("글 전체 조회 테스트 입니다.")
    void 게시글_전체조회_테스트() {
        // given
        List<Post> posts = List.of(
                new Post(null, "제목1", "내용1"),
                new Post(null, "제목2", "내용2"),
                new Post(null, "제목3", "내용3")
        );
        postRepository.saveAll(posts);

        // when
        List<PostGetResponse> responses = postService.getAllPosts();

        // then
        assertEquals(3, responses.size());
        assertEquals("제목1", responses.get(0).getTitle());
        assertEquals("제목2", responses.get(1).getTitle());
        assertEquals("제목3", responses.get(2).getTitle());
    }

    @Test
    @DisplayName("게시글 제목수정 테스트입니다..")
    void 게시글_제목수정_테스트() {
        //given
        Post post = Post.builder() // Post엔티티 객체를 만들어서 title,과 content를 DB에 저장 (id는 GeneratedValue에 의해 자동생성)
                .title("제목입니다.")
                .content("내용입니다.")
                .build();
        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("수정된 제목입니다.")
                .content("내용입니다.")
                .build();

        // when
        postService.editPost(post.getId(), postEdit);

        // then
        Post changePost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재 하지 않습니다. id" + post.getId()));
        assertEquals("수정된 제목입니다.", changePost.getTitle());
    }

    @Test
    @DisplayName("게시글 삭제 테스트입니다")
    public void 게시글이_잘지워지나요() {
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
}