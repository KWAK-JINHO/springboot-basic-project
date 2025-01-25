package com.jinoprac.springboot_prac.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jinoprac.springboot_prac.entity.Post;
import com.jinoprac.springboot_prac.repository.post.PostRepository;
import com.jinoprac.springboot_prac.request.PostCreate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api.jinoboard.com", uriPort = 443)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class PostControllerDocTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Test
    @DisplayName("게시글 목록 조회")
    void 게시글_목록_조회() throws Exception {
        // given
        Post post1 = Post.builder()
                .title("테스트 제목 1")
                .content("테스트 내용 1")
                .build();
        Post post2 = Post.builder()
                .title("테스트 제목 2")
                .content("테스트 내용 2")
                .build();
        postRepository.save(post1);
        postRepository.save(post2);

        // expected
        mockMvc.perform(RestDocumentationRequestBuilders.get("/posts")
                        .param("page", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andDo(document("post-list",
                        RequestDocumentation.queryParameters(
                                RequestDocumentation.parameterWithName("page").description("조회할 페이지 (0부터 시작)")
                                        .attributes(Attributes.key("constraint").value("0 이상 숫자"))
                        ),
                        PayloadDocumentation.responseFields(
                                PayloadDocumentation.fieldWithPath("[].id").description("게시글 ID"),
                                PayloadDocumentation.fieldWithPath("[].title").description("게시글 제목"),
                                PayloadDocumentation.fieldWithPath("[].content").description("게시글 내용"),
                                PayloadDocumentation.fieldWithPath("[].createAt").description("게시글 작성 시간")
                        )
                ));
    }

    @Test
    @DisplayName("게시글 단건 조회")
    void 게시글_단건_조회() throws Exception {
        // given
        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .build();
        postRepository.save(post);

        // expected
        mockMvc.perform(RestDocumentationRequestBuilders.get("/posts/{postId}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andDo(document("post-inquiry",
                        RequestDocumentation.pathParameters(
                                RequestDocumentation.parameterWithName("postId").description("게시글 ID")
                            ),
                        PayloadDocumentation.responseFields(
                                PayloadDocumentation.fieldWithPath("id").description("게시글 ID"),
                                PayloadDocumentation.fieldWithPath("title").description("제목"),
                                PayloadDocumentation.fieldWithPath("content").description("내용"),
                                PayloadDocumentation.fieldWithPath("createAt").description("생성 시간")
                        )
                        ));
    }

    @Test
    @DisplayName("게시글 등록")
    void 게시글_작성() throws Exception {
        // given
        PostCreate postCreate = new PostCreate("제목입니다", "내용입니다");

        // expected
        mockMvc.perform(RestDocumentationRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postCreate))
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andDo(document("post-create",
                        PayloadDocumentation.requestFields(
                                PayloadDocumentation.fieldWithPath("title").description("제목")
                                        .attributes(Attributes.key("constraint").value("제목을 입력해주세요.")),
                                PayloadDocumentation.fieldWithPath("content").description("내용").optional()
                        )
                        ));
    }

    @Test
    @DisplayName("게시글 수정")
    void 게시글_수정() throws Exception {
        // given
        Post post = Post.builder()
                .title("기존 제목")
                .content("기존 내용")
                .build();
        postRepository.save(post);

        PostCreate postEdit = new PostCreate("수정된 제목", "수정된 내용");

        // expected
        mockMvc.perform(RestDocumentationRequestBuilders.patch("/posts/{postId}", post.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postEdit))
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andDo(document("post-edit",
                        RequestDocumentation.pathParameters(
                                RequestDocumentation.parameterWithName("postId").description("수정할 게시글 ID")
                        ),
                        PayloadDocumentation.requestFields(
                                PayloadDocumentation.fieldWithPath("title").description("수정할 제목")
                                        .attributes(Attributes.key("constraint").value("제목은 필수 입력값입니다.")),
                                PayloadDocumentation.fieldWithPath("content").description("수정할 내용").optional()
                        ),
                        PayloadDocumentation.responseFields(
                                PayloadDocumentation.fieldWithPath("id").description("게시글 ID")
                        )
                ));
    }

    @Test
    @DisplayName("게시글 삭제")
    void 게시글_삭제() throws Exception {
        // given
        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .build();
        postRepository.save(post);

        // expected
        mockMvc.perform(RestDocumentationRequestBuilders.delete("/posts/{postId}", post.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNoContent())
                .andDo(document("post-delete",
                        RequestDocumentation.pathParameters(
                                RequestDocumentation.parameterWithName("postId").description("삭제할 게시글 ID")
                        )
                ));
    }

    @Test
    @DisplayName("게시글 검색")
    void 게시글_검색() throws Exception {
        // given
        Post post1 = Post.builder()
                .title("테스트 제목")
                .content("테스트 내용")
                .build();
        Post post2 = Post.builder()
                .title("검색 제목")
                .content("검색 내용")
                .build();
        postRepository.save(post1);
        postRepository.save(post2);

        // expected
        mockMvc.perform(RestDocumentationRequestBuilders.get("/posts/search")
                        .param("keyword", "검색")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andDo(document("post-search",
                        RequestDocumentation.queryParameters(
                                RequestDocumentation.parameterWithName("keyword").description("검색 키워드")
                        ),
                        PayloadDocumentation.responseFields(
                                PayloadDocumentation.fieldWithPath("[].id").description("게시글 ID"),
                                PayloadDocumentation.fieldWithPath("[].title").description("게시글 제목"),
                                PayloadDocumentation.fieldWithPath("[].content").description("게시글 내용"),
                                PayloadDocumentation.fieldWithPath("[].createAt").description("게시글 작성 시간")
                        )
                ));
    }
}
