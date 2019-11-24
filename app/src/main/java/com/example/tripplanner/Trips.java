package com.example.tripplanner;

import java.util.Arrays;

public class Trips {
    String creator,title,cover_image;
    Double latitude,longitude;
    String[] added_users;

    @Override
    public String toString() {
        return "Trips{" +
                "creator='" + creator + '\'' +
                ", title='" + title + '\'' +
                ", cover_image='" + cover_image + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", added_users=" + Arrays.toString(added_users) +
                '}';
    }
}
