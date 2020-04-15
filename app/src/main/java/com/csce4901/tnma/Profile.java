package com.csce4901.tnma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Profile extends AppCompatActivity {
    private Button homeButton;
    private ViewPager viewPager;
    private TextView profileEmail;
    private CardView eventViewer;

    private TextView profileName;
    private TextView profilePhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        homeButton = (Button) findViewById(R.id.profileHomeButton);
        profileName = (TextView) findViewById(R.id.profileName);
        profileEmail = (TextView) findViewById(R.id.profileEmail);
        profilePhone = (TextView) findViewById(R.id.profilePhone);
        eventViewer = (CardView) findViewById(R.id.profileEventViewer);
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        eventViewer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent
                            = new Intent(getApplicationContext(),
                            profile_event_viewer.class);
                    startActivity(intent);
                }
        });
        profileEmail.setText(email);


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
        

        ///---- updating textview for user information
        profileName = (TextView) findViewById(R.id.profileName);
        profilePhone = (TextView) findViewById(R.id.profilePhone);

        ///---- using method to update textviews
        //GeneralUserDao.sampleMethod(profileName, profilePhone);
    }
    //String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();


}
