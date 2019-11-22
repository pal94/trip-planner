package com.example.tripplanner;

import java.util.List;

public class Trip {
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Trip(String title, Double latitude, Double longitude, String url) {
        this.title = title;
        this.latitude = latitude;
        this.longitude = longitude;
        this.url = url;
    }

    String title,  url;
    Double latitude, longitude;

}
