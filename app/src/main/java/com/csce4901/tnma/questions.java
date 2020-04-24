package com.csce4901.tnma;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class questions extends AppCompatActivity {

    ListView listView;
    String questions[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        listView = (ListView) findViewById(R.id.list_questions);

        // gets array from string.xml- replace this from values in database
        // questions- just retrieve questions you have recently asked
        questions = getResources().getStringArray(R.array.event_address);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,questions);
        listView.setAdapter(arrayAdapter);
    }
}
