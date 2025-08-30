package com.globant.model;

public class AdminUser extends User{
    private final String role = "admin";
    public AdminUser(String name, String lastName, String email, String password) {
        super(name, lastName, email, password);
    }
}
