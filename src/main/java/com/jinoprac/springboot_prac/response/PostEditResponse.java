package com.jinoprac.springboot_prac.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class PostEditResponse {

    private final Long id;
}
