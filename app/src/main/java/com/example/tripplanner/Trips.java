package com.example.tripplanner;

import java.util.Arrays;
import java.util.List;

public class Trips {
    String creator,title,cover_image,name;
    Double latitude,longitude;
    List<String> added_users;

    @Override
    public String toString() {
        return "Trips{" +
                "creator='" + creator + '\'' +
                ", title='" + title + '\'' +
                ", cover_image='" + cover_image + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", added_users=" + added_users +
                '}';
    }
}