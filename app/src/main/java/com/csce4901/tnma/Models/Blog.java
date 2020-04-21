package com.csce4901.tnma.Models;

import com.google.firebase.Timestamp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Blog {
    Date dt;
    String title;
    String post;
    String imageURL;
    String author;
    int boostCount = 0;
    int commentCount = 0;
    Map<String, Boolean> boostedByUser;   // Map <Email, hasBoosted?>
    Map<String, List<String>> comments;   // Map <commentId, <Comment, Email, Date>>
    boolean isFeatured = false;

    public Blog(){ }
    public Blog(String title, String post, String uri_image, String author, Integer boostCount, Integer commentCount){
        this.title = title;
        this.post = post;
        this.imageURL = uri_image;
        this.dt = new Date();
        this.author = author;
        this.boostCount = boostCount;
        this.boostedByUser = new HashMap<>();
        this.comments = new HashMap<>();
        this.commentCount = commentCount;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImageURL() {return imageURL;}

    public void setImageURL(String uri_image) {this.imageURL = uri_image;}

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public int getBoostCount() {
        return boostCount;
    }

    public void setBoostCount(int boostCount) {
        this.boostCount = boostCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public Map<String, List<String>> getComments() {
        return comments;
    }

    public void setComments(Map<String, List<String>> comments) {
        this.comments = comments;
    }

    public Map<String, Boolean> getBoostedByUser() { return boostedByUser; }

    public void setBoosts(Map<String, Boolean> boostedByUser) { this.boostedByUser = boostedByUser; }

    public boolean isFeatured() { return isFeatured; }

    public void setFeatured(boolean featured) { isFeatured = featured; }
}