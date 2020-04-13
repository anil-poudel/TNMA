package com.csce4901.tnma.DAO.Impl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.csce4901.tnma.Connector.FirebaseConnector;
import com.csce4901.tnma.DAO.EventDao;
import com.csce4901.tnma.EventAdapter;
import com.csce4901.tnma.MainActivity;
import com.csce4901.tnma.Models.Event;
import com.csce4901.tnma.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.csce4901.tnma.Constants.UserConstant.FS_EVENTS_COLLECTION;
import static com.csce4901.tnma.Constants.UserConstant.FS_EVENT_ENROLLED_USERS;
import static com.csce4901.tnma.Constants.UserConstant.IS_FEATURED;

public class EventDaoImpl implements EventDao {
    FirebaseConnector fbConnector = new FirebaseConnector();

    @Override
    public void createNewEvent(String title, String description, String address, String date, String time) {
        Event newEvent = new Event(title, description, address, date, time);
        fbConnector.firebaseSetup();
        FirebaseFirestore db = fbConnector.getDb();
        db.collection(FS_EVENTS_COLLECTION).document(title).set(newEvent)
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
        DocumentReference docRef = db.collection(FS_EVENTS_COLLECTION).document(eventTitle);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                assert document != null;
                if (document.exists()) {
                    Event event = document.toObject(Event.class);
                    List<String> existingUsersForEvent = event.getEnrolledUsers();
                    existingUsersForEvent.add(newUserEmail);
                    db.collection(FS_EVENTS_COLLECTION)
                            .document(eventTitle)
                            .set(event, SetOptions.mergeFields(FS_EVENT_ENROLLED_USERS));
                } else {
                    Log.e(TAG, "Event does not exist: " + eventTitle);
                }
            } else {
                Log.e(TAG, "get failed with ", task.getException());
            }
        });
    }

    @Override
    public void getAllEvents(RecyclerView recyclerView, FragmentActivity activity, int[] images) {
        fbConnector.firebaseSetup();
        FirebaseFirestore db = fbConnector.getDb();
        db.collection(FS_EVENTS_COLLECTION)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<String> title_list = new LinkedList<>();
                        List<String> desc_list = new LinkedList<>();
                        List<String> addr_list = new LinkedList<>();
                        List<String> time_list = new LinkedList<>();
                        List<String> date_list = new LinkedList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Event event = document.toObject(Event.class);
                            title_list.add(event.getTitle());
                            desc_list.add(event.getDescription());
                            addr_list.add(event.getAddress());
                            time_list.add(event.getTime());
                            date_list.add(event.getDate());
                        }
                        String[] event_title = title_list.toArray(new String[0]);
                        String[] event_desc = desc_list.toArray(new String[0]);
                        String[] event_addr = addr_list.toArray(new String[0]);
                        String[] event_time = time_list.toArray(new String[0]);
                        String[] event_date = date_list.toArray(new String[0]);

                        EventAdapter eventAdapter = new EventAdapter(activity,
                                event_title,event_desc,event_addr,event_time,event_date,images);
                        recyclerView.setAdapter(eventAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
                    } else {
                        Log.e(TAG, "Error getting documents: ", task.getException());
                    }
                });
    }

    @Override
    public void getFeaturedEvents(TextView title, TextView desc, ImageView image) {
        fbConnector.firebaseSetup();
        FirebaseFirestore db = fbConnector.getDb();
        Query featuredEventQuery = db.collection(FS_EVENTS_COLLECTION).whereEqualTo(IS_FEATURED, true).limit(1);
        featuredEventQuery.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                String f_event_title = "No featured event";
                String f_event_desc = "No featured event";
                String f_event_img_URL = null;
                Bitmap bitmap = null; 
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Event featuredEvent = document.toObject(Event.class);
                     f_event_title = featuredEvent.getTitle();
                     f_event_desc = featuredEvent.getDescription();
                     f_event_img_URL = featuredEvent.getImageURL();
                }
                title.setText(f_event_title);
                desc.setText(f_event_desc);
                if(f_event_img_URL == null || f_event_img_URL.isEmpty()){
                    image.setImageResource(R.drawable.event_default);
                } else {
                    try {
                        bitmap = BitmapFactory.decodeStream((InputStream)new URL(f_event_img_URL).getContent());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    image.setImageBitmap(bitmap);
                }
            } else {
                Log.e(TAG, "Failed to get featured event ");
            }
        });
    }

    @Override
    public void deleteEvent(String eventTitle) {
        fbConnector.firebaseSetup();
        FirebaseFirestore db = fbConnector.getDb();
        DocumentReference docRef = db.collection(FS_EVENTS_COLLECTION).document(eventTitle);
        docRef.delete();
    }

    public void manageEventBtnVisibility(Button btn, String email, String eventTitle){
        fbConnector.firebaseSetup();
        FirebaseFirestore db = fbConnector.getDb();
        DocumentReference docRef = db.collection(FS_EVENTS_COLLECTION).document(eventTitle);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                assert document != null;
                if (document.exists()) {
                    List<String> event_attendee = (List<String>) document.get("enrolledUsers");
                    if(event_attendee.contains(email)){
                        Log.i(TAG, "User already registered for this event");
                        btn.setVisibility(View.INVISIBLE);
                    }
                    else{
                        Log.i(TAG, "User not yet registered for this event");
                    }
                } else {
                    Log.e(MainActivity.class.getName(), "No such document for event " + eventTitle);
                }
            } else {
                Log.e(MainActivity.class.getName(), "get failed with ", task.getException());
            }
        });
    }
}
