package com.jinoprac.springboot_prac.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class PostGetResponse {

    private final Long id;
    private final String title;
    private final String content;
    private final LocalDateTime createAt;
}
