package com.globant;

import com.globant.model.AdminUser;
import com.globant.model.User;
import com.globant.repository.UserRepository;
import com.globant.repository.impl.UserFileRepository;
import com.globant.service.UserService;
import com.globant.service.impl.UserServiceImpl;
import com.globant.session.impl.InMemorySessionManager;
import com.globant.session.SessionManager;
import com.globant.storage.Storage;
import com.globant.storage.impl.JsonFileStorage;
import com.globant.ui.ConsoleUI;

public class Main {
    public static void main(String[] args) {
        Storage<User> userStorage = new JsonFileStorage<>("users.json", User.class);
        UserRepository userRepository = new UserFileRepository(userStorage);
        UserService userService = new UserServiceImpl(userRepository);
        SessionManager sessionManager = new InMemorySessionManager();

        createDefaultAdmin(userService, userRepository);

        ConsoleUI ui = new ConsoleUI(userService, sessionManager);

        ui.start();
    }

    private static void createDefaultAdmin(UserService userService, UserRepository userRepository) {
        String adminEmail = "admin@system.com";
        if (userRepository.findByEmail(adminEmail).isEmpty()) {
        System.out.println("Creating default admin");
            AdminUser admin = new AdminUser(
                    "Admin",
                    "System",
                    adminEmail,
                    "Admin123"
            );
            userRepository.save(admin);
            System.out.println("⚙️ Default admin user created: " + adminEmail);
        }
    }
}