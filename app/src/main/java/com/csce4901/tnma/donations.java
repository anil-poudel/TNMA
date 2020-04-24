package com.csce4901.tnma;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class donations extends AppCompatActivity {

    ListView listView;
    String donations[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donations);

        listView = (ListView) findViewById(R.id.list_donations);

        // gets array from string.xml- replace this from values in database
        // "donation + amount" for each string in array- concatenate strings
        donations = getResources().getStringArray(R.array.event_address);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,donations);
        listView.setAdapter(arrayAdapter);
    }
}