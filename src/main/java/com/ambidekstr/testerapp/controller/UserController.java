package com.ambidekstr.testerapp.controller;

import com.ambidekstr.testerapp.domain.User;
import com.ambidekstr.testerapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

/**
 * Created by anatolii on 08.08.2016.
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public ResponseEntity<User> loginPage(){
        User user = new User();
        user.setFirstName("Anatolii");
        user.setLastName("Voloshyn");
        user.setEmail("aavolosh@gmail.com");
        user.setPassword("password");
        userService.saveUser(user);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @RequestMapping("/use")
    public ResponseEntity<UUID> lnPage(){
        User user = userService.fingUserByID(2L);

        return new ResponseEntity<>(user.getUuid(),HttpStatus.OK);
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<UUID> addUser(@RequestBody User user){
        int a = 10;
        userService.saveUser(user);
        return new ResponseEntity<>(user.getUuid(),HttpStatus.OK);
    }
}
