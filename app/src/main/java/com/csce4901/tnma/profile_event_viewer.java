package com.csce4901.tnma;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.csce4901.tnma.DAO.EventDao;
import com.csce4901.tnma.DAO.Impl.EventDaoImpl;
import com.google.firebase.auth.FirebaseAuth;

public class profile_event_viewer extends AppCompatActivity {

    ListView listView;
    String data[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_event_viewer);

        listView = findViewById(R.id.list_event);
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        EventDao eventDao = new EventDaoImpl();
        eventDao.getAllEventsListView(email, listView, this);
    }
}