package com.diginals.microlancer;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by jessica on 2016-09-25.
 */
@IgnoreExtraProperties
public class User {
    public String username;
    public String email;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }
}