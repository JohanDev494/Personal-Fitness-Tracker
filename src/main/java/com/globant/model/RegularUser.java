package com.globant.model;

public class RegularUser extends User{
    public RegularUser(String name, String lastName, String email, String password) {
        super(name, lastName, email, password);
        super.setRol("USER");
    }
}
