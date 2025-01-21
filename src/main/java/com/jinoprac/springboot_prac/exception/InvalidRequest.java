package com.jinoprac.springboot_prac.exception;

import lombok.Getter;

/**
 * status -> 400
 */
@Getter
public class InvalidRequest extends BoardApplicationException {

    private static final String Message = "잘못된 요청입니다.";

    public InvalidRequest() {
        super(Message);
    }

    public InvalidRequest(String fieldName, String message) {
        super(Message);
        addValidation(fieldName, message);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
