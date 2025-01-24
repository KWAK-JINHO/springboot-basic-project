package com.jinoprac.springboot_prac.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PostSearch(
        @NotBlank(message = "키워드를 입력해주세요.")
        @Size(min = 1, max = 20, message = "최소 1글자 이상 입력해야 합니다.")
        String keyword
) {}