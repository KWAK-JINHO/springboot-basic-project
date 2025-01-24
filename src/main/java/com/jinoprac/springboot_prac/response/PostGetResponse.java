package com.jinoprac.springboot_prac.response;

import com.jinoprac.springboot_prac.entity.Post;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PostGetResponse(
        Long id,
        String title,
        String content,
        LocalDateTime createAt
) {
    public static PostGetResponse from(Post post) {
        return PostGetResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createAt(post.getCreateAt())
                .build();
    }
}