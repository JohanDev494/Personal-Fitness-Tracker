package com.globant.model;

public class AdminUser extends User{
    public AdminUser(Long id, String name, String lastName, String email, String password) {
        super(id, name, lastName, email, password);
    }
}
