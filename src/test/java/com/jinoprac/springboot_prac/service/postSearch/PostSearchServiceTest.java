package com.jinoprac.springboot_prac.service.postSearch;

import com.jinoprac.springboot_prac.entity.Post;
import com.jinoprac.springboot_prac.repository.post.PostRepository;
import com.jinoprac.springboot_prac.request.PostSearch;
import com.jinoprac.springboot_prac.response.PostGetResponse;
import com.jinoprac.springboot_prac.service.PostService;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class PostSearchServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private Validator validator;

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
        PostSearch postSearch = new PostSearch("후후");

        // when
        List<PostGetResponse> responses = postService.searchPost(postSearch);

        // then
        assertFalse(responses.isEmpty());
        assertEquals(3, responses.size());
        assertTrue(responses.stream()
                .allMatch(response -> response.title().contains("후후") || response.content().contains("후후"))
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
        PostSearch postSearch = new PostSearch("gngn");

        // when
        List<PostGetResponse> responses = postService.searchPost(postSearch);

        // then
        assertTrue(responses.isEmpty());
    }

    @Test
    @DisplayName("검색어는 공백을 제외한 1글자 이상이어야 한다.")
    void 공백을_포함하더라도_한글자_이상이면_유효하다() {
        // Given
        PostSearch postSearch = new PostSearch("   a   ");

        // When
        Set<ConstraintViolation<PostSearch>> violations = validator.validate(postSearch);

        // Then
        assertTrue(violations.isEmpty());
    }
}
