package com.jinoprac.springboot_prac.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jinoprac.springboot_prac.entity.Post;
import com.jinoprac.springboot_prac.repository.post.PostRepository;
import com.jinoprac.springboot_prac.request.PostCreate;
import com.jinoprac.springboot_prac.request.PostEdit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.*;
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
    @DisplayName("/posts 요청 테스트")
    void 게시글_작성_요청() throws Exception {
        // given
        PostCreate postCreate = PostCreate.builder()
                .title("제목입니다")
                .content("내용입니다")
                .build();

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
        PostCreate postCreate = PostCreate.builder()
                .content("내용입니다")
                .build();

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

        PostEdit postEdit = PostEdit.builder()
                .title("수정된 제목입니다")
                .content("내용입니다")
                .build();

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
    void 게시글_작성시_바보_금지() throws Exception {
        // given
        PostCreate postCreate = PostCreate.builder()
                .title("무료판매중")
                .content("내용입니다")
                .build();

        // expected
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postCreate)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}
