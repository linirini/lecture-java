package com.lecture.exception;

import java.util.Optional;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String INVALID_METHOD_ARGUMENT_MESSAGE = "요청 형식이 잘못되었습니다.";
    private static final String INTERNAL_SERVER_ERROR_MESSAGE = "예기치 못한 서버 오류입니다. 다시 시도해주세요.";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String exceptionMessage = Optional.of(e.getBindingResult().getFieldError())
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse(INVALID_METHOD_ARGUMENT_MESSAGE);
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST.toString(), exceptionMessage);
        return ResponseEntity.badRequest().body(exceptionResponse);
    }

    @ExceptionHandler(LectureException.class)
    @ApiResponse(responseCode = "400")
    public ResponseEntity<ExceptionResponse> handleLectureException(LectureException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST.toString(), e.getMessage());
        return ResponseEntity.badRequest().body(exceptionResponse);
    }

    @ExceptionHandler(RuntimeException.class)
    @ApiResponse(responseCode = "500")
    public ResponseEntity<ExceptionResponse> handleInternalServerErrorException(RuntimeException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR.toString(), INTERNAL_SERVER_ERROR_MESSAGE);
        return ResponseEntity.internalServerError().body(exceptionResponse);
    }
}
