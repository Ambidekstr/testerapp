package com.ambidekstr.testerapp.controller;

import com.ambidekstr.testerapp.domain.User;
import com.ambidekstr.testerapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * Created by anatolii on 08.08.2016.
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    private final String ROOT = "C:/upload-dir";

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
        User user = userService.getUserByEmail(email);
        if(!(user==null)){
            return new ResponseEntity<>("First name: "+user.getFirstName()+" Last name: "+user.getLastName(),HttpStatus.OK);
        }else{
            return new ResponseEntity<>("No such user",HttpStatus.OK);
        }

    }


    @RequestMapping(value = "/user/{uuid}/receipts", method = RequestMethod.GET)
    public ModelAndView fileUploadForm(@PathVariable UUID uuid){
        return new ModelAndView("fileupload");
    }
    @RequestMapping(value = "/user/{uuid}/receipts", method = RequestMethod.POST)
    public ResponseEntity<String> fileUpload(@PathVariable UUID uuid, @RequestParam(name="file", value = "file") MultipartFile file){
        userService.fingUserByID(uuid);
        if (!file.isEmpty()) {
            try {
                Files.copy(file.getInputStream(), Paths.get(ROOT, file.getOriginalFilename()));
            } catch (IOException |RuntimeException e) {
                return new ResponseEntity<>("Failed to upload file", HttpStatus.OK);
            }
            return new ResponseEntity<>("File was successfully upload", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to upload file", HttpStatus.OK);
        }

    }
}
