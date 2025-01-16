package com.jinoprac.springboot_prac.request;

import com.jinoprac.springboot_prac.exception.InvalidRequest;
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

    public void validate() {
        if (title.contains("바보")) {
            throw new InvalidRequest("title", "제목에 바보를 포함할 수 없습니다.");
        }
    }
}
