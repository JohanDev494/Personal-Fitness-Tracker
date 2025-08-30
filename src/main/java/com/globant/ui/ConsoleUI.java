package com.globant.ui;

import com.globant.service.UserService;
import com.globant.session.SessionManager;

import java.util.Scanner;

public class ConsoleUI {
    private final UserService userService;
    private final Scanner scanner;
    private final SessionManager sessionManager;

    public ConsoleUI(UserService userService, SessionManager sessionManager) {
        this.userService = userService;
        this.scanner = new Scanner(System.in);
        this.sessionManager = sessionManager;
    }

    public void start() {
        while (true) {
            System.out.println("\n==== Welcome to the Personal Fitness Tracker ====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Please select an option: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> new UserRegistrationUI(userService, scanner).show();
                case 2 -> new LoginUI(userService, scanner, sessionManager).show();
                case 3 -> {
                    System.out.println("exit");
                    return;
                }
                default -> System.out.println("Invalid option, try again.");
            }
        }
    }
//    private void registerUser() {
//        System.out.print("Enter name: ");
//        String name = scanner.nextLine();
//
//        System.out.print("Enter last name: ");
//        String lastname = scanner.nextLine();
//
//        System.out.print("Enter email: ");
//        String email = scanner.nextLine();
//
//        System.out.print("Enter password: ");
//        String password = scanner.nextLine();
//
//        try {
//            userService.registerUser(name,lastname,email, password);
//            System.out.println("✅ User registered successfully!");
//        } catch (IllegalArgumentException e) {
//            System.out.println("⚠️ " + e.getMessage());
//        }
//    }

//    private void listUsers() {
//        List<User> users = userService.listUsers();
//        if (users.isEmpty()) {
//            System.out.println("⚠️ No users registered yet.");
//        } else {
//            System.out.println("==== Registered Users ====");
//            users.forEach(u -> System.out.println("ID: " + u.getId() + " | Username: " + u.getUsername()));
//        }
//    }

//    private void findUser() {
//        System.out.print("Enter username: ");
//        String username = scanner.nextLine();
//
//        Optional<User> user = userService.findByUsername(username);
//        user.ifPresentOrElse(
//                u -> System.out.println("✅ Found user: " + u.getUsername() + " (ID: " + u.getId() + ")"),
//                () -> System.out.println("⚠️ User not found")
//        );
//    }

//    private void deleteUser() {
//        System.out.print("Enter user ID to delete: ");
//        long id = scanner.nextLong();
//        scanner.nextLine();
//
//        userService.deleteUser(id);
//        System.out.println("✅ User deleted (if it existed).");
//    }
}
