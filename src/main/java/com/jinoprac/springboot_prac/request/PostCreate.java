package com.jinoprac.springboot_prac.request;

import com.jinoprac.springboot_prac.entity.Post;
import com.jinoprac.springboot_prac.validator.ForbiddenWords;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PostCreate(
        @NotBlank(message = "제목은 필수 입력값 입니다.")
        @Size(min = 1, max = 15, message = "제목은 1글자 이상 15글자 이하여야 합니다.")
        @ForbiddenWords(words = {"무료", "최저가", "할인", "구매"})
        String title,

        @NotBlank(message = "내용은 필수 입력값 입니다.")
        @Size(min = 1, max = 1000, message = "내용은 1자이상 1000자 이하여야 합니다.")
        String content
) {
    public Post toEntity() {
        return Post.builder()
                .title(this.title)
                .content(this.content)
                .build();
    }
}
