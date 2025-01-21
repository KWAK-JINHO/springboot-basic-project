package com.jinoprac.springboot_prac.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
public class PostGetResponse {

    private final Long id;
    private final String title;
    private final String content;
    private final LocalDateTime createAt;
}
