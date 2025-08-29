package com.globant;

import com.globant.model.AdminUser;
import com.globant.model.User;
import com.globant.repository.UserRepository;
import com.globant.repository.impl.UserFileRepository;
import com.globant.service.UserService;
import com.globant.service.impl.UserServiceImpl;
import com.globant.storage.Storage;
import com.globant.storage.impl.FileStorage;
import com.globant.storage.impl.JsonFileStorage;
import com.globant.ui.UserConsoleUI;

public class Main {
    public static void main(String[] args) {
        Storage<User> userStorage = new JsonFileStorage<>("users.json", User.class);
        UserRepository userRepository = new UserFileRepository(userStorage);
        UserService userService = new UserServiceImpl(userRepository);

        createDefaultAdmin(userService);

        UserConsoleUI userConsoleUI = new UserConsoleUI(userService);

        userConsoleUI.start();
    }

    private static void createDefaultAdmin(UserService userService) {
        String adminEmail = "admin@system.com";
        if (userService.findByEmail(adminEmail).isEmpty()) {
            AdminUser admin = new AdminUser(
                    "Admin",
                    "System",
                    "a@a.test",
                    "Admin123"
            );
            userService.registerUser(admin);
            System.out.println("⚙️ Default admin user created: " + adminEmail);
        }
    }
}