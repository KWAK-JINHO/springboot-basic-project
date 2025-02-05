package com.jinoprac.springboot_prac.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jinoprac.springboot_prac.entity.Post;
import com.jinoprac.springboot_prac.repository.post.PostRepository;
import com.jinoprac.springboot_prac.request.PostCreate;
import com.jinoprac.springboot_prac.request.PostEdit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("게시글 등록이 잘 돼요")
    void 게시글_작성() throws Exception {
        // given
        PostCreate postCreate = new PostCreate("제목입니다", "내용입니다");

        // expected
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postCreate))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andDo(print());
    }

    @Test
    @DisplayName("/posts 요청시 title값은 필수로 있어야 한다.")
    void 게시글_작성_제목누락() throws Exception {
        // given
        PostCreate postCreate = new PostCreate(null, "내용입니다.");

        // expected
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postCreate))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.title").value("제목은 필수 입력값 입니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("글 1개 조회")
    void 글_1개_조회() throws Exception {
        // given
        Post post = Post.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();
        Post savedPost = postRepository.save(post);

        // expected
        mockMvc.perform(get("/posts/{postId}", savedPost.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("글 전체가 불러와져야 한다.")
    void 게시글_전체_조회() throws Exception {
        // given
        Post post = Post.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();
        Post savedPost = postRepository.save(post);

        // expected
        mockMvc.perform(get("/posts/{postId}", savedPost.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 수정 테스트")
    void 게시글_수정이_잘_돼요() throws Exception {
        //given
        Post post = Post.builder()
                .title("제목입니다")
                .content("내용입니다")
                .build();
        postRepository.save(post);

        PostEdit postEdit = new PostEdit("수정된 제목입니다", "내용입니다");

        //expected
        mockMvc.perform(patch("/posts/{postId}", post.getId())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postEdit))
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 삭제 테스트")
    void 게시글_삭제_테스트() throws Exception {
        // given
        Post post = Post.builder()
                .title("제목입니다")
                .content("내용입니다")
                .build();
        postRepository.save(post);

        // expected
        mockMvc.perform(delete("/posts/{postId}", post.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 삭제 - 존재하지 않는 글")
    void 존재하지_않는_게시글_삭제() throws Exception {

        // expected
        mockMvc.perform(delete("/posts/{postId}", 1L)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 작성 시 제목에 금지단어는 표시될 수 없다.")
    void 게시글_작성시_금지단어_체크() throws Exception {
        // given
        PostCreate postCreate = new PostCreate("무 료판매중", "내용입니다");

        // expected
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postCreate)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Tag("postSearchTest")
    @Test
    @DisplayName("게시글 키워드가 있을 때 게시글 검색 성공")
    void 게시글_검색기능_성공_테스트() throws Exception {
        // given
        List<Post> posts = List.of(
                Post.builder().title("진호1").content("내용1").build(),
                Post.builder().title("진2").content("내용2").build(),
                Post.builder().title("정호3").content("내용진호3").build()
        );
        postRepository.saveAll(posts);

        // expected
        mockMvc.perform(get("/posts/search")
                        .param("keyword", "진호")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2)) // 검색된 게시글 수 확인
                .andExpect(jsonPath("$[0].title").value("정호3"))
                .andExpect(jsonPath("$[1].title").value("진호1"))
                .andDo(print());
    }
}
