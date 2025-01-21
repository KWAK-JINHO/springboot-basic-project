package com.jinoprac.springboot_prac.request;

import com.jinoprac.springboot_prac.exception.InvalidRequest;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostCreate {

    @NotBlank(message = "제목은 필수 입력값 입니다.")
    String title;

    @NotBlank(message = "내용은 필수 입력값 입니다.")
    String content;

    public void validate() {
        String[] prohibitedWords = {"무료", "최저가", "할인", "구매"};
        for (String word : prohibitedWords) {
            if (title.contains(word)) {
                throw new InvalidRequest("title", "제목에 사용할 수 없는 단어가 있습니다.");
            }
        }
    }
}
