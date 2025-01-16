package com.jinoprac.springboot_prac.controller;

import com.jinoprac.springboot_prac.exception.BoardApplicationException;
import com.jinoprac.springboot_prac.exception.InvalidRequest;
import com.jinoprac.springboot_prac.exception.PostNotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import com.jinoprac.springboot_prac.response.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j // 로깅을 위한 어노테이션 log.error() 등으로 로그 사용 가능
@ControllerAdvice // 모든 컨트롤러의 예외를 처리
public class ExceptionController {

    @ResponseBody // ErrorResponse를 JSON형태로 응답한다.
    @ResponseStatus(HttpStatus.BAD_REQUEST) // HTTP 응답코드를 400으로 설정
    @ExceptionHandler(MethodArgumentNotValidException.class) // MethodArgumentNotValidException -> @Valid 사용해서 필드값 검증 시 검증에 실패할 때 걸러준다.
    // 위처럼 작성하면 MethodArgumentNotValidException 에 관해서만 캐치한다.
    // 스프링에서 제공해주는 Exceptionn은 그 종류 마다 처리해줘야 하는것들이 조금 달라서 따로 구현
    public ErrorResponse invalidRequestHandler(MethodArgumentNotValidException e) {
        ErrorResponse response = ErrorResponse.builder()
                .code("400")
                .message("잘못된 요청입니다.")
                .build();

        for (FieldError fieldError : e.getFieldErrors()) {
            response.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return response;
    }

    // 비지니스가 커지면 Exceoption이 많아 지기 때문에 비지니스에 맞는 최상위 Exception을 하나 만들어서 관리해준다.
    @ResponseBody
    @ExceptionHandler(BoardApplicationException.class)
    public ResponseEntity<ErrorResponse> boardApplicationException(BoardApplicationException e) {
        int statusCode = e.getStatusCode();

        ErrorResponse body = ErrorResponse.builder()
                .code(String.valueOf(statusCode)) // statusCode의 타입을 int로 두어도 되지만 String으로 두었을때 이점이 있기 때문에 이렇게 사용
                .message(e.getMessage())
                .validation(e.getValidation())
                .build();

        ResponseEntity<ErrorResponse> response = ResponseEntity.status(statusCode)
                .body(body);

        return response;
    }
}
