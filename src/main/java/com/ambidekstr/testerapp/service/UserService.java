package com.ambidekstr.testerapp.service;

import com.ambidekstr.testerapp.domain.User;

import java.util.List;
import java.util.UUID;

/**
 * Created by anatolii on 08.08.2016.
 */
public interface UserService {
    void saveUser(User user);
    User fingUserByID(UUID uuid);
    boolean emailCheck(String email);
    List<User> getAllUsers();
    boolean checkLogin(String email, String password);
    User getUserByEmail(String email);
}
