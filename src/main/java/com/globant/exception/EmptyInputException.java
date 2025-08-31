package com.globant.exception;

public class EmptyInputException extends InvalidInputException {
    public EmptyInputException() {
        super("⚠️ Input cannot be empty");
    }
}

