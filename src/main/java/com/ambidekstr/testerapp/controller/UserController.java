package com.ambidekstr.testerapp.controller;

import com.ambidekstr.testerapp.domain.MetaData;
import com.ambidekstr.testerapp.domain.User;
import com.ambidekstr.testerapp.service.MetaDataService;
import com.ambidekstr.testerapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.UUID;

/**
 * Created by anatolii on 08.08.2016.
 * Controller for all requests
 * addUser - saves new user to database and return his uuid;
 * loginUser - check if email and password is correct and return a token for this login;
 * findUserByEmail - check if email is correct if so return users first and last name;
 * fileUploadForm - return file upload page;
 * fileUpload - check if file is not empty and then save it and record metadata to database;
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MetaDataService metaDataService;

    public UserController(UserService userService, MetaDataService metaDataService){
        this.userService = userService;
        this.metaDataService = metaDataService;
    }

    private final String ROOT = "E:/";

    @RequestMapping(value = "/user", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<String> addUser(@RequestBody User user){
        if(!userService.emailCheck(user.getEmail())){
            userService.saveUser(user);
            return new ResponseEntity<>(user.getUuid().toString(),HttpStatus.OK);
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

    @RequestMapping(value = "/user/{email:.+}", method = RequestMethod.GET)
    public ResponseEntity<Object> findUserByEmail(@PathVariable String email){
        User user = userService.getUserByEmail(email);
        if(!(user==null)){
            return new ResponseEntity<>("First name: "+user.getFirstName()+" Last name: "+user.getLastName(),HttpStatus.OK);
        }else{
            return new ResponseEntity<>("No such user",HttpStatus.OK);
        }

    }


    @RequestMapping(value = "/user/receipts", method = RequestMethod.GET)
    public ModelAndView fileUploadForm(){
        return new ModelAndView("fileupload");
    }

    @RequestMapping(value = "/user/{uuid}/receipts", method = RequestMethod.POST)
    public ResponseEntity<?> fileUpload(@PathVariable UUID uuid, @RequestParam(name="file") MultipartFile file){
        if (!file.isEmpty()) {
            try {
                InputStream is = file.getInputStream();
                Files.copy(is, Paths.get(ROOT, file.getOriginalFilename()));
                BasicFileAttributes attr = Files.readAttributes(Paths.get(ROOT, file.getOriginalFilename()), BasicFileAttributes.class);
                MetaData metaData = new MetaData(uuid,attr.creationTime().toString(),attr.size());
                metaDataService.saveMetaData(metaData);
                is.close();
            } catch (IOException |RuntimeException e) {
                e.printStackTrace();
                return new ResponseEntity<>("Failed to upload file", HttpStatus.OK);
            }
            return new ResponseEntity<>("File was successfully uploaded", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to upload file", HttpStatus.OK);
        }

    }
}
