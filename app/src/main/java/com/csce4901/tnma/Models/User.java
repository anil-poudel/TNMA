package com.csce4901.tnma.Models;

import java.util.List;

public abstract class User {

    private String email;
    // role 0 -> admin, 1 -> general user, 2 -> student, 3 -> mentor
    private int role;
    private boolean roleVerified;
    private List<Integer> events;

    public User(String email, boolean roleVerified){
        this.email = email;
        this.role = 1;  //default role set to general user
        this.roleVerified = roleVerified;
    }

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public boolean isRoleVerified() {
        return roleVerified;
    }

    public List<Integer> getEvents() {
        return events;
    }

    public void setEvents(List<Integer> events) {
        this.events = events;
    }
}
