package com.example.tripplanner;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User implements Serializable {
    String first,last,email,password,gender,avatar;
    ArrayList<String> tripid = new ArrayList<>();

    public User(String first, String last, String email, String password, String gender, String avatar) {
        this.first = first;
        this.last = last;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.avatar = avatar;
    }

    public Map toHashMap(){
        Map<String, Object> user = new HashMap<>();
        user.put("firstName",this.first);
        user.put("lastName",this.last);
        user.put("email",this.email);
        user.put("password",this.password);
        user.put("gender",this.gender);
        user.put("avatar",this.avatar);
        user.put("tripId", this.tripid);
        return user;
    }

    @Override
    public String toString() {
        return "User{" +
                "first='" + first + '\'' +
                ", last='" + last + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
