package com.globant.exception;

public class OutOfRangeException extends RuntimeException {
    public OutOfRangeException() {
        super("⚠️ Your option is out of range");
    }
}
