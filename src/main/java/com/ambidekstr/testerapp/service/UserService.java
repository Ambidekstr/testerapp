package com.ambidekstr.testerapp.service;

import com.ambidekstr.testerapp.domain.User;

/**
 * Created by anatolii on 08.08.2016.
 */
public interface UserService {
    void saveUser(User user);
    User fingUserByID(Long id);
}
