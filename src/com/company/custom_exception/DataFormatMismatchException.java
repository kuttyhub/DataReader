package com.company.custom_exception;

public class DataFormatMismatchException extends Exception {
    public DataFormatMismatchException(String message) {
        super(message);
    }
}
