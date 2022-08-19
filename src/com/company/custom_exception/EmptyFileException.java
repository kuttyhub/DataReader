package com.company.custom_exception;

public class EmptyFileException extends Exception {
    public EmptyFileException(String message) {
        super(message);
    }
}
