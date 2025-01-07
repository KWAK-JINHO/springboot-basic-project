package com.jinoprac.springboot_prac.response;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@Builder
public class PostCreateResponse {

    private Long id;
}
