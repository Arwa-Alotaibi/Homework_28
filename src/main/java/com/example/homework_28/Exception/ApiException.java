package com.example.homework_28.Exception;

public class ApiException extends RuntimeException {
    public ApiException(String msg) {
        super(msg);
    }
}