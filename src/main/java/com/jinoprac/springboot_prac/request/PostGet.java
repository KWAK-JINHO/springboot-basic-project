package com.jinoprac.springboot_prac.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostGet {

    @NotBlank(message = "id 는 필수 입력값 입니다.")
    private Long id;
}
