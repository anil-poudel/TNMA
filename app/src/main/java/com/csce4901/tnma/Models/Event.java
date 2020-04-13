package com.csce4901.tnma.Models;

import java.util.ArrayList;
import java.util.List;

public class Event {
    String title;   // required
    String description; //optional
    String address; //required
    String date;    //required
    String time;    //required
    String imageURL;
    boolean isFeatured = false;
    List<String> enrolledUsers;

    public Event(){ }
    public Event(String title, String description, String address, String date, String time){
        this.title = title;
        this.description = description;
        this.address = address;
        this.date = date;
        this.time = time;
        this.enrolledUsers = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFeatured() {
        return isFeatured;
    }

    public void setFeatured(boolean featured) {
        isFeatured = featured;
    }

    public List<String> getEnrolledUsers() {
        return enrolledUsers;
    }

    public void setEnrolledUsers(List<String> enrolledUsers) {
        this.enrolledUsers = enrolledUsers;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
