package com.group3b.project.repositories;

import com.group3b.project.models.User;

public interface IUserRepository {
    boolean addUser(User user);
    User getUser(String email);
}
