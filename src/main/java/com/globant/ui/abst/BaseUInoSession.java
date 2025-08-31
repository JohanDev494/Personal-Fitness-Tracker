package com.globant.ui.abst;

import com.globant.service.UserService;
import java.util.Scanner;

public abstract class BaseUInoSession {
    protected final UserService userService;
    protected final Scanner scanner;

    public BaseUInoSession(UserService userService, Scanner scanner) {
        this.userService = userService;
        this.scanner = scanner;
    }
}