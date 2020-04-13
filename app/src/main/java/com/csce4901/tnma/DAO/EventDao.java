package com.csce4901.tnma.DAO;

import android.widget.Button;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public interface EventDao {
    // create event
    public void createNewEvent(String title, String description, String address, String date, String time);
    // add user to event
    public void addUserToEvent(String email, String eventTitle);
    // retrieve all events
    public void getAllEvents(RecyclerView recyclerView, FragmentActivity fragmentActivity, int[] images);
    // delete an event
    public void deleteEvent(String eventTitle);
    // disable join event button if already registered for event
    public void manageEventBtnVisibility(Button btn, String email, String eventTitle);

    }
