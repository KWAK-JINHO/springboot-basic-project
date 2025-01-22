package com.jinoprac.springboot_prac.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostEdit {

    @NotBlank(message = "타이틀을 입력하세요.")
    String title;

    @NotBlank(message = "콘텐츠를 입력해주세요.")
    String content;
}
