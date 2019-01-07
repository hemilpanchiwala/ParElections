package com.example.android.parliamentaryelections;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User1 {
    public String name;
    public String email;

    public User1() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User1(String username, String email) {
        this.name = username;
        this.email = email;
    }
}