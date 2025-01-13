package com.jinoprac.springboot_prac.response;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Builder
@Getter
public class PostCreateResponse {

    private final Long id;
}
