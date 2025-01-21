package com.jinoprac.springboot_prac.response;

import com.jinoprac.springboot_prac.entity.Post;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Builder
@Getter
public class PostCreateResponse {

    private final Long id;
}
