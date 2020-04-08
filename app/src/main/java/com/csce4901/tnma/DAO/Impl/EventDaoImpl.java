package com.csce4901.tnma.DAO.Impl;

import android.util.Log;

import com.csce4901.tnma.Connector.FirebaseConnector;
import com.csce4901.tnma.DAO.EventDao;
import com.csce4901.tnma.Models.Event;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class EventDaoImpl implements EventDao {
    FirebaseConnector fbConnector = new FirebaseConnector();

    @Override
    public void createNewEvent(String title, String description, String address, String date, String time) {
        Event newEvent = new Event(title, description, address, date, time);
        fbConnector.firebaseSetup();
        FirebaseFirestore db = fbConnector.getDb();
        db.collection("events").document(title).set(newEvent)
                .addOnSuccessListener(aVoid -> {
                    Log.i(TAG, "Event detail stored in database for: " + title);
                })
                .addOnFailureListener(aVoid -> {
                    Log.e(TAG, "Unable to store user detail for: " + title);
                });
    }

    @Override
    public void addUserToEvent(String newUserEmail, String eventTitle) {
        fbConnector.firebaseSetup();
        FirebaseFirestore db = fbConnector.getDb();
        DocumentReference docRef = db.collection("events").document(eventTitle);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                assert document != null;
                if (document.exists()) {
                    Event event = document.toObject(Event.class);
                    System.out.println("EVENT RETRIEVED ----> " + event.toString());
                    List<String> existingUsersForEvent = event.getEnrolledUsers();
                    if(existingUsersForEvent == null){
                        System.out.println("<<<<< EVENTS USER LIST WAS NULL >>>>");
                    }
                    existingUsersForEvent.add(newUserEmail);
                    db.collection("events")
                            .document(eventTitle)
                            .set(event, SetOptions.mergeFields("enrolledUsers"));
                } else {
                    Log.e(TAG, "Event does not exist: " + eventTitle);
                }
            } else {
                Log.e(TAG, "get failed with ", task.getException());
            }
        });
    }

    @Override
    public void listAllEvents() {

    }
}
