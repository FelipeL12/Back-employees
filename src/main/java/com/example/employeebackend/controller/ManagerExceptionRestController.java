package com.example.employeebackend.controller;

import com.example.employeebackend.dto.ErrorResponse;
import com.example.employeebackend.exceptions.BadRequestException;
import com.example.employeebackend.exceptions.ObjectNotExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class ManagerExceptionRestController extends ResponseEntityExceptionHandler {

    //    ERROR BAD NOT FOUND
    @ExceptionHandler(ObjectNotExistsException.class)
    public ResponseEntity<ErrorResponse> ObjectNoExists(ObjectNotExistsException e) {

        var response = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    //    ERROR BAD REQUEST

    //  Bad Request Activity
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> BadRequestActivity(BadRequestException e) {

        var response = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    //    Incorrect Data
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ErrorResponse> IncorrectData(Exception e) {

        e.printStackTrace();
        var response = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    //    ERROR INTERAL_SERVER_ERROR
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ErrorResponse> InternalServerError(Exception e) {

        e.printStackTrace();
        var response = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> HandleMethodArgumentNoValid(@NonNull MethodArgumentNotValidException exception, @NonNull HttpHeaders headers, @NonNull HttpStatus status, WebRequest request) {

        List<String> ErrorValidations = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());
        return GetExceptionResponseEntity(HttpStatus.BAD_REQUEST, ErrorValidations);
    }

    private ResponseEntity<Object> GetExceptionResponseEntity(final HttpStatus status, List<String> errors) {

        List<ErrorResponse> errores = !CollectionUtils.isEmpty(errors) ? errors.stream().map(ErrorResponse::new).collect(Collectors.toList()) : Collections.singletonList(new ErrorResponse(status.getReasonPhrase()));
        return new ResponseEntity<>(errores, status);
    }
}
