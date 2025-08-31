package com.globant.ui.abst;

import com.globant.service.UserService;
import com.globant.session.SessionManager;
import java.util.Scanner;

public abstract class BaseUI {
    protected final UserService userService;
    protected final SessionManager sessionManager;
    protected final Scanner scanner;

    public BaseUI(UserService userService, SessionManager sessionManager, Scanner scanner) {
        this.userService = userService;
        this.sessionManager = sessionManager;
        this.scanner = scanner;
    }
}
