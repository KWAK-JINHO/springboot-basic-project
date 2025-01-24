package com.jinoprac.springboot_prac.response;

import com.jinoprac.springboot_prac.entity.Post;
import lombok.Builder;

@Builder
public record PostCreateResponse(
        Long id
) {
    public static PostCreateResponse from(Post post) {
        return PostCreateResponse.builder()
                .id(post.getId())
                .build();
    }
}
