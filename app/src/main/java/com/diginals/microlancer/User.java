package com.diginals.microlancer;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jessica on 2016-09-25.
 */
@IgnoreExtraProperties
public class User {

    public String username;
    public String email;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public User() {

    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
