package com.csce4901.tnma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Profile extends AppCompatActivity {
    private Button homeButton;
    private ViewPager viewPager;
    private TextView viewText;
    private CardView eventViewer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        homeButton = (Button) findViewById(R.id.profileHomeButton);
        viewText = (TextView) findViewById(R.id.profileName);
        eventViewer = (CardView) findViewById(R.id.profileEventViewer);
        eventViewer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent
                            = new Intent(getApplicationContext(),
                            profile_event_viewer.class);
                    startActivity(intent);
                }
        });




        viewText.setText("Sid");


        homeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent
                        = new Intent(getApplicationContext(),
                        Dashboard.class);
                startActivity(intent);
            }
        });
        ///------ future buttons
    }
    //String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
}
