package com.ambidekstr.testerapp.service;

import com.ambidekstr.testerapp.domain.User;
import com.ambidekstr.testerapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
    public User fingUserByID(UUID uuid) {
        return userRepository.findOne(uuid);
    }

    @Override
    public boolean emailCheck(String email) {
        List<User> users = userRepository.findAll();
        Map<String,User> usersWithEmails = new HashMap<>();
        for (User u:users) {
            usersWithEmails.put(u.getEmail(),u);
        }
        return usersWithEmails.containsKey(email);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public boolean checkLogin(String email, String password) {
        List<User> users = userRepository.findAll();
        Map<String,String> usersWithPasswords = new HashMap<>();
        for (User u:users) {
            usersWithPasswords.put(u.getEmail(),u.getPassword());
        }
        if (usersWithPasswords.containsKey(email)){
            if(usersWithPasswords.get(email).equals(password)){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }

    }

    @Override
    public User getUserByEmail(String email) {
        List<User> users = userRepository.findAll();
        Map<String,User> usersWithEmails = new Hashtable<>();
        for (User u:users) {
            usersWithEmails.put(u.getEmail(),u);
        }
        if(usersWithEmails.containsKey(email)) {
            return usersWithEmails.get(email);
        }else{
            return null;
        }
    }
}
