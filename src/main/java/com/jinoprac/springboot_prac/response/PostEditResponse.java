package com.jinoprac.springboot_prac.response;

import com.jinoprac.springboot_prac.entity.Post;
import lombok.Builder;

@Builder
public record PostEditResponse(
        Long id
) {
    public static PostEditResponse from(Post post) {
        return PostEditResponse.builder()
                .id(post.getId())
                .build();
    }
}