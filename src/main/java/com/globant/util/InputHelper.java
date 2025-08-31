package com.globant.util;

import com.globant.exception.EmptyInputException;
import com.globant.exception.NotANumberException;
import com.globant.exception.OutOfRangeException;

import java.util.Scanner;

public class InputHelper {
    private final Scanner scanner;

    public InputHelper(Scanner scanner) {
        this.scanner = scanner;
    }

    public int readInt(String prompt, int min, int max) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();

        if (input.isBlank()) {
            throw new EmptyInputException();
        }

        try {
            int option = Integer.parseInt(input);
            if (option > max || option < min) {
                throw new OutOfRangeException();
            }
            return option;
        } catch (NumberFormatException e) {
            throw new NotANumberException();
        }
    }

    public String readString(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();

        if (input.isBlank()) {
            throw new EmptyInputException();
        }

        return input;
    }
}
