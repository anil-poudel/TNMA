package com.csce4901.tnma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.csce4901.tnma.DAO.GeneralUserDao;
import com.csce4901.tnma.DAO.Impl.GeneralUserDaoImpl;
import com.google.firebase.auth.FirebaseAuth;

public class Profile extends AppCompatActivity implements OnItemSelectedListener{
    private Button homeButton;
    private Button statusButton;
    private ViewPager viewPager;
    private TextView profileEmail;
    private CardView eventViewer;
    private CardView questionViewer;

    private TextView profileName;
    private TextView profilePhone;
    private TextView profileRole;

    private Drawable[] compoundDrawables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        homeButton = (Button) findViewById(R.id.profileHomeButton);
        statusButton = (Button) findViewById(R.id.buttonStatus);
        profileName = (TextView) findViewById(R.id.profileName);
        profileEmail = (TextView) findViewById(R.id.profileEmail);
        profilePhone = (TextView) findViewById(R.id.profilePhone);
        profileRole = (TextView) findViewById(R.id.profileRole);
        eventViewer = (CardView) findViewById(R.id.profileEventViewer);
        questionViewer = (CardView) findViewById(R.id.questions);
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        GeneralUserDao user = new GeneralUserDaoImpl();
        user.getUserProfileInfo(email, profileName, profilePhone, profileRole);

        compoundDrawables = profileName.getCompoundDrawables();
        Drawable topCompoundDrawable = compoundDrawables[1];
        Drawable img = getApplicationContext().getResources().getDrawable(R.drawable.bellcon);

        profileName.setCompoundDrawablesRelativeWithIntrinsicBounds(compoundDrawables[0], img, compoundDrawables[2], compoundDrawables[3]);
        eventViewer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent
                            = new Intent(getApplicationContext(),
                            profile_event_viewer.class);
                    startActivity(intent);
                }
        });
        questionViewer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent
                        = new Intent(getApplicationContext(),
                        questions.class);
                startActivity(intent);
            }
        });
        profileEmail.setText(email);
        homeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
        });
        statusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent
                            = new Intent(getApplicationContext(),
                            ContactPage.class);
                    startActivity(intent);
                }
        });
        ///------ future buttons
        ///---- updating textview for user information
        profileName = (TextView) findViewById(R.id.profileName);
        profilePhone = (TextView) findViewById(R.id.profilePhone);

        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinner1);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);
        spinner.setSelection(0, false);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add(0, "CHANGE AVATAR");
        categories.add("Scientist");
        categories.add("TNMA");
        categories.add("Gold Medal");
        categories.add("Mushroom");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) {
            Drawable img = getApplicationContext().getResources().getDrawable(R.drawable.profile);
            profileName.setCompoundDrawablesRelativeWithIntrinsicBounds(compoundDrawables[0], img, compoundDrawables[2], compoundDrawables[3]);
        }
        else {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
        compoundDrawables = profileName.getCompoundDrawables();
        if (item == "TNMA") {
            Drawable img = getApplicationContext().getResources().getDrawable(R.drawable.tnma);
            profileName.setCompoundDrawablesRelativeWithIntrinsicBounds(compoundDrawables[0], img, compoundDrawables[2], compoundDrawables[3]);
        } else if (item == "Scientist") {
            Drawable img = getApplicationContext().getResources().getDrawable(R.drawable.scientist);
            profileName.setCompoundDrawablesRelativeWithIntrinsicBounds(compoundDrawables[0], img, compoundDrawables[2], compoundDrawables[3]);
        } else if (item == "Mushroom") {
            Drawable img = getApplicationContext().getResources().getDrawable(R.drawable.profile);
            profileName.setCompoundDrawablesRelativeWithIntrinsicBounds(compoundDrawables[0], img, compoundDrawables[2], compoundDrawables[3]);
        } else if (item == "Gold Medal") {
            Drawable img = getApplicationContext().getResources().getDrawable(R.drawable.goldmedal);
            profileName.setCompoundDrawablesRelativeWithIntrinsicBounds(compoundDrawables[0], img, compoundDrawables[2], compoundDrawables[3]);
        }
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
        }
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}
