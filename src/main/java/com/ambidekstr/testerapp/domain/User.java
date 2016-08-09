package com.ambidekstr.testerapp.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

/**
 * Created by anatolii on 08.08.2016.
 */
@Entity
public class User implements Serializable {

    @Column(name = "lastName",nullable = false)
    private String lastName;

    @Column(name = "firstName",nullable = false)
    private String firstName;

    @Column(name = "email",nullable = false, unique = true)
    private String email;

    @Column(name = "password",nullable = false)
    private String password;

    @Id
    @Column(name = "UUID", nullable = false,updatable = false)
    private UUID uuid;

    public User(){
        this.uuid = UUID.randomUUID();
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UUID getUuid() {
        return uuid;
    }
}
