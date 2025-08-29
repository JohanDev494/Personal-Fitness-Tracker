package com.globant.model;

public class RegularUser extends User{
    private final String role = "user";
    public RegularUser(String name, String lastName, String email, String password) {
        super(name, lastName, email, password);
    }
    public String getRole() {
        return role;
    }
}
