package com.example.JobPortalApplication.service;

import com.example.JobPortalApplication.entity.Users;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UsersService {
    Users addNew(Users users);

    Optional<Users>getUserByEmail(String email);

    Object getCurrentUserProfile();

    Users getCurrentUser();

    Users findByEmail(String currentUsername);
}
