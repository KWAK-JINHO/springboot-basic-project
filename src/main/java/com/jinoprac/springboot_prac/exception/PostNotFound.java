package com.jinoprac.springboot_prac.exception;

/**
 * status -> 404 (이상적인 PostNotFound의 상태코드)
 */
public class PostNotFound extends BoardApplicationException  {

    private static final String MESSAGE = "존재하지 않는 글입니다.";

    public PostNotFound() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
