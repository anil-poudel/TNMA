package com.csce4901.tnma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;

public class Questionnaire extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);
        getAnswer();
    }

    public void getAnswer(){
        Button submitButton = findViewById(R.id.submitAnswer);
        submitButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.getAppContext(), Dashboard.class);
            startActivity(intent);
        });
    }


}
