package com.jinoprac.springboot_prac.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostSearch {

    @NotBlank(message = "키워드를 입력해주세요.")
    String keyword;
}