package com.company.custom_exception;

public class InvalidDataTypeException extends Exception {
    public InvalidDataTypeException(String message) {
        super(message);
    }
}
