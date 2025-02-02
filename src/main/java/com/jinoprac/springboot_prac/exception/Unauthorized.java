package com.jinoprac.springboot_prac.exception;

/**
 * status -> 401
 */
public class Unauthorized extends BoardApplicationException {

    private static final String MESSAGE = "유효하지 않은 인증정보입니다.";

    public Unauthorized() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 401;
    }
}
