package com.csce4901.tnma;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class profile_event_viewer extends AppCompatActivity {

    ListView listView;
    String data[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_event_viewer);

        listView = (ListView) findViewById(R.id.list_event);

        // gets array from string.xml- replace this from values in database
        data = getResources().getStringArray(R.array.event_title);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,data);
        listView.setAdapter(arrayAdapter);
    }
}