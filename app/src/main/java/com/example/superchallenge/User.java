package com.example.superchallenge;

public class User {
    public String userID;
    public int count;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String userID, int count) {
        this.userID = userID;
        this.count = count;
    }
}
