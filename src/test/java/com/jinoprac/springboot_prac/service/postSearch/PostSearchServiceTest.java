package com.jinoprac.springboot_prac.service.postSearch;

import com.jinoprac.springboot_prac.entity.Post;
import com.jinoprac.springboot_prac.repository.post.PostRepository;
import com.jinoprac.springboot_prac.request.PostSearch;
import com.jinoprac.springboot_prac.response.PostGetResponse;
import com.jinoprac.springboot_prac.service.PostService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class PostSearchServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("게시글 키워드 검색 테스트")
    void 게시글_검색기능_성공_테스트() {
        // given
        List<Post> posts = List.of(
                Post.builder().title("제목후후").content("내용후후").build(),
                Post.builder().title("제목후후").content("내용하하").build(),
                Post.builder().title("제후목").content("내용후후").build(),
                Post.builder().title("제목후").content("내용캬캬").build()
        );
        postRepository.saveAll(posts);
        PostSearch postSearch = PostSearch.builder()
                .keyword("후후")
                .build();

        // when
        List<PostGetResponse> responses = postService.searchPost(postSearch);

        // then
        assertFalse(responses.isEmpty());
        assertEquals(3, responses.size());
        assertTrue(responses.stream()
                .allMatch(response -> response.getTitle().contains("후후") || response.getContent().contains("후후"))
        );

    }

    @Test
    @DisplayName("게시글 검색시 키워드가 존재하지 않아요")
    void 게시글_검색기능_검색키워드가_존재하지_않아요() {
        // given
        List<Post> posts = List.of(
                Post.builder().title("제목후후").content("내용후후").build(),
                Post.builder().title("제목후후").content("내용하하").build(),
                Post.builder().title("제후후목").content("내용히히").build(),
                Post.builder().title("제목후").content("내용캬캬").build()
        );
        postRepository.saveAll(posts);
        PostSearch postSearch = PostSearch.builder()
                .keyword("gngn")
                .build();

        // when
        List<PostGetResponse> responses = postService.searchPost(postSearch);

        // then
        assertTrue(responses.isEmpty());
    }
}
