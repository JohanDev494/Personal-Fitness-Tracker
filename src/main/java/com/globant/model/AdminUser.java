package com.globant.model;

public class AdminUser extends User{
    public AdminUser(String name, String lastName, String email, String password) {
        super(name, lastName, email, password);
        super.setRol("ADMIN");
    }
}
