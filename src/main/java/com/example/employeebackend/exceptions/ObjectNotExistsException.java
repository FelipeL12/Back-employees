package com.example.employeebackend.exceptions;

public class ObjectNotExistsException extends RuntimeException {

    public ObjectNotExistsException(String message) {
        super(message);
    }
}
