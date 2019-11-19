package com.example.tripplanner;

import android.graphics.Bitmap;

import java.io.Serializable;

public class User implements Serializable {
    String first,last,email,password,gender,avatar;

    public User(String first, String last, String email, String password, String gender, String avatar) {
        this.first = first;
        this.last = last;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.avatar = avatar;
    }
}
