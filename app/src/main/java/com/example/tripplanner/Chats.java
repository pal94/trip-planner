package com.example.tripplanner;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Chats implements Serializable {

    String id;
    String tripTitle;
    String message, fname,lname,  avtar;
    String emailofsender;

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getAvtar() {
        return avtar;
    }

    public void setAvtar(String avtar) {
        this.avtar = avtar;
    }

    public String getEmailofsender() {
        return emailofsender;
    }

    public void setEmailofsender(String emailofsender) {
        this.emailofsender = emailofsender;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    List<String> receivername;
    List<String> reciversavatar;
    String time;
}
