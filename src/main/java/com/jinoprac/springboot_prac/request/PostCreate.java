package com.jinoprac.springboot_prac.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostCreate {

    @NotBlank(message = "제목은 필수 입력값 입니다.")
    private String title;

    @NotBlank(message = "내용은 필수 입력값 입니다.")
    private String content;

}
