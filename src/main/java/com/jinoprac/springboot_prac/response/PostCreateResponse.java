package com.jinoprac.springboot_prac.response;

import lombok.Builder;

import java.sql.Timestamp;

@Builder
public class PostCreateResponse {

    private String title;
    private String content;
    private Timestamp createAt;
}
