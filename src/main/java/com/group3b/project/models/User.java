package com.group3b.project.models;

import java.util.UUID;

public class User {
    private UUID id;
    private String email;
    private String password;

    public User(String email, String password) {
        this(UUID.randomUUID(), email, password);
    }
    public User(UUID id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public User() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
