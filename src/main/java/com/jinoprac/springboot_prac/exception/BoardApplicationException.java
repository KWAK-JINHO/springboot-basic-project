package com.jinoprac.springboot_prac.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class BoardApplicationException extends RuntimeException {

    public final Map<String, String> validation = new HashMap<>();

    public BoardApplicationException(String message) {
        super(message);
    }

    public BoardApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int getStatusCode();

    public void addValidation(String fieldName, String message) {
        validation.put(fieldName, message);
    }
}
