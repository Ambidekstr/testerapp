package com.ambidekstr.testerapp.controller;

import com.ambidekstr.testerapp.domain.User;
import com.ambidekstr.testerapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.UUID;

/**
 * Created by anatolii on 08.08.2016.
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Object> addUser(@RequestBody User user){
        if(!userService.emailCheck(user.getEmail())){
            userService.saveUser(user);
            return new ResponseEntity<>(user.getUuid(),HttpStatus.OK);
        }else{
            return new ResponseEntity<>("This email already used",HttpStatus.OK);
        }

    }

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public ResponseEntity<Object> loginUser(@RequestParam String email, @RequestParam String password){
        if(userService.checkLogin(email,password)){
            return new ResponseEntity<>(UUID.randomUUID(),HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Login failed! Email or password is incorrect",HttpStatus.OK);
        }

    }

    @RequestMapping(value = "/user/{email}", method = RequestMethod.GET)
    public ResponseEntity<Object> findUserByEmail(@PathVariable String email){
        int a=10;
        User user = userService.getUserByEmail(email);
        if(!(user==null)){
            return new ResponseEntity<>("First name: "+user.getFirstName()+" Last name: "+user.getLastName(),HttpStatus.OK);
        }else{
            return new ResponseEntity<>("No such user",HttpStatus.OK);
        }

    }

    @RequestMapping(value = "/user/{uuid}/receipts", method = RequestMethod.POST)
}
