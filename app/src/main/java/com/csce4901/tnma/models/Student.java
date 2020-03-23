package com.csce4901.tnma.models;

import java.util.List;

public class Student extends User {

    private String fname;
    private String lname;
    private String phone;
    private String city;
    private String state;
    private String school;
    private String address;
    private String mentor;
    private List<Integer> questions;
    private List<Integer> blogPost;

    public Student(String email, String fname, String lname, String phone, String city, String state){
        super(email, false);
        super.setRole(2);
        this.fname = fname;
        this.lname = lname;
        this.phone = phone;
        this.city = city;
        this.state = state;
    }

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMentor() {
        return mentor;
    }

    public void setMentor(String mentor) {
        this.mentor = mentor;
    }

    public List<Integer> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Integer> questions) {
        this.questions = questions;
    }

    public List<Integer> getBlogPost() {
        return blogPost;
    }

    public void setBlogPost(List<Integer> blogPost) {
        this.blogPost = blogPost;
    }
}
