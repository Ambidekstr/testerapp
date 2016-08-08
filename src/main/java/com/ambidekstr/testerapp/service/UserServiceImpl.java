package com.ambidekstr.testerapp.service;

import com.ambidekstr.testerapp.domain.User;
import com.ambidekstr.testerapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by anatolii on 08.08.2016.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User fingUserByID(Long id) {
        return userRepository.findOne(id);
    }
}
