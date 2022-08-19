package com.company.custom_exception;

public class UnknownFileTypeException extends Exception {
    public UnknownFileTypeException(String message) {
        super(message);
    }
}
