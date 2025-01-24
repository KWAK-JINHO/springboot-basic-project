//package com.jinoprac.springboot_prac.mapper;
//
//import com.jinoprac.springboot_prac.entity.Post;
//import com.jinoprac.springboot_prac.request.PostCreate;
//import com.jinoprac.springboot_prac.response.PostGetResponse;
//import org.junit.jupiter.api.Test;
//import org.mapstruct.factory.Mappers;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//class PostMapperTest {
//
//    private final PostMapper postMapper = Mappers.getMapper(PostMapper.class);
//
//    @Test
//    void testToEntity() {
//        // given
//        PostCreate postCreate = new PostCreate("Test Title", "Test Content");
//
//        // when
//        Post post = postMapper.toEntity(postCreate);
//
//        // then
//        assertThat(post.getId()).isNull(); // id는 무시
//        assertThat(post.getTitle()).isEqualTo("Test Title");
//        assertThat(post.getContent()).isEqualTo("Test Content");
//        assertThat(post.getCreateAt()).isNotNull(); // 엔티티 생성 시 자동 설정
//    }
//
//    @Test
//    void testToGetResponse() {
//        // given
//        Post post = Post.builder()
//                .title("Test Title")
//                .content("Test Content")
//                .build();
//
//        // when
//        PostGetResponse response = postMapper.toGetResponse(post);
//
//        // then
//        assertThat(response.getId()).isEqualTo(post.getId());
//        assertThat(response.getTitle()).isEqualTo(post.getTitle());
//        assertThat(response.getContent()).isEqualTo(post.getContent());
//        assertThat(response.getCreateAt()).isEqualTo(post.getCreateAt());
//    }
//}
