package com.jinoprac.springboot_prac.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * {
 *     "code": "400",
 *     "message": "잘못된 요청입니다."
 *     "validation": {
 *         "title": "값을 입력해주세요."
 *     }
 * }
 * 위처럼 validation json object 를 생성해서 오류메세지 표기하는 방법을 사용하겠다.
 */

@Getter
//@JsonInclude(value = JsonInclude.Include.NON_EMPTY)  클라이언트가 비어있지 않은 데이터만 내려달라고할 때 이렇게 해주면 설정 가능하다.
public class ErrorResponse {

    private final String code;
    private final String message;
    private final Map<String, String> validation;

    @Builder
    public ErrorResponse(String code, String message, Map<String, String> validation) {
        this.code = code;
        this.message = message;
        this.validation = validation != null ? validation : new HashMap<>();
    }

    public void addValidation(String fieldName, String errorMessage) {
        this.validation.put(fieldName, errorMessage);
    }
}
