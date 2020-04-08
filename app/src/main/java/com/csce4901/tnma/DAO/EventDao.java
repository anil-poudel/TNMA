package com.csce4901.tnma.DAO;

public interface EventDao {
    // create event
    public void createNewEvent(String title, String description, String address, String date, String time);
    // add user to event
    public void addUserToEvent(String email, String eventTitle);
    // retrieve all events
    public void listAllEvents();
}
