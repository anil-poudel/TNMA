package com.csce4901.tnma.Models;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Blog {
    Date dt;
    String title;
    String post;
    Map<String, String> comments;   // Map <Email, Comment>
    boolean isFeatured = false;

    public Blog(){ }
    public Blog(String title, String post){
        this.title = title;
        this.post = post;
        this.dt = new Date();
        this.comments = new HashMap<>();
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public Map<String, String> getComments() {
        return comments;
    }

    public void setComments(Map<String, String> comments) {
        this.comments = comments;
    }

    public boolean isFeatured() {
        return isFeatured;
    }

    public void setFeatured(boolean featured) {
        isFeatured = featured;
    }
}