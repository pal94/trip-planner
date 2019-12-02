package com.example.tripplanner;

import java.io.Serializable;

public class Place implements Serializable {
    String place_id,description,title,latitude,longitude;

    public Place(String place_id, String description) {
        this.place_id = place_id;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Place{" +
                "place_id='" + place_id + '\'' +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }
}
