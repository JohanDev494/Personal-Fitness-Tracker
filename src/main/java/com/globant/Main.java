package com.globant;

import com.globant.model.User;
import com.globant.repository.UserRepository;
import com.globant.repository.impl.UserFileRepository;
import com.globant.service.UserService;
import com.globant.service.impl.UserServiceImpl;
import com.globant.storage.Storage;
import com.globant.storage.impl.FileStorage;
import com.globant.storage.impl.JsonFileStorage;
import com.globant.ui.UserConsoleUI;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Storage<User> userStorage = new JsonFileStorage<>("users.json", User.class);
        UserRepository userRepository = new UserFileRepository(userStorage);
        UserService userService = new UserServiceImpl(userRepository);

        UserConsoleUI userConsoleUI = new UserConsoleUI(userService);

        userConsoleUI.start();
    }
}