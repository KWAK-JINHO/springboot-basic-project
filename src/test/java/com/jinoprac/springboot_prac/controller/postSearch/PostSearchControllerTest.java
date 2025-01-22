package com.jinoprac.springboot_prac.controller.postSearch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jinoprac.springboot_prac.entity.Post;
import com.jinoprac.springboot_prac.repository.post.PostRepository;
import com.jinoprac.springboot_prac.request.PostSearch;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class PostSearchControllerTest {

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
