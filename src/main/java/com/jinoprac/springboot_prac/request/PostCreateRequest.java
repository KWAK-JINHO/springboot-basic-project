package com.jinoprac.springboot_prac.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateRequest {

    @NotBlank(message = "제목은 필수 입력값 입니다.")
    private String title;

    @NotBlank(message = "내용은 필수 입력값 입니다.")
    private String content;

}