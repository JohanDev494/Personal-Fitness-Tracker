package com.globant.exception;

public class NotANumberException extends InvalidInputException {
    public NotANumberException() {
        super("⚠️ Input must be a valid number");
    }
}
