package com.csce4901.tnma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.csce4901.tnma.DAO.Impl.StudentDaoImpl;
import com.csce4901.tnma.DAO.Impl.MentorDaoImpl;
import com.csce4901.tnma.DAO.MentorDao;
import com.csce4901.tnma.DAO.StudentDao;
import com.csce4901.tnma.Models.Mentor;
import com.csce4901.tnma.Models.Student;
import com.csce4901.tnma.Models.User;
import com.google.firebase.auth.FirebaseAuth;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class Questionnaire extends AppCompatActivity  {

    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;
    private TextView[] mDots;
    private  SliderAdapter sliderAdapter;


    //declaring widgets from UI
    private Button nNextButton;
    private Button nBackButton;
    private Spinner nUserType;
    private EditText nfNameField;
    private EditText nlNameField;
    private EditText ncityField;
    private EditText nStateField;
    private EditText nphonefield;

    //user details (first name, last name, city, state, phone)
    private String first_name;
    private String last_name;
    private String city;
    private String state;
    private String phone;
    private String user_type;
    //current page tracker
    private int nCurrentPage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);

        mSlideViewPager = (ViewPager) findViewById(R.id.SlideViewPager);
        mDotLayout = (LinearLayout) findViewById(R.id.dotsLayout);
        nNextButton = (Button) findViewById(R.id.nextBtn);
        nBackButton = (Button) findViewById(R.id.prevBtn);
        nUserType = (Spinner) findViewById(R.id.userrole);
        nfNameField = (EditText) findViewById(R.id.fnamefield);
        nlNameField = (EditText) findViewById(R.id.lnamefield);
        ncityField = (EditText) findViewById(R.id.cityfield);
        nStateField = (EditText) findViewById(R.id.statefield);
        nphonefield = (EditText) findViewById(R.id.phonefield);


        //Implementation of slider view
        sliderAdapter = new SliderAdapter(this);
        mSlideViewPager.setAdapter(sliderAdapter);
        addDotsIndicator(0);
        mSlideViewPager.addOnPageChangeListener(viewListener);

        //OnclickLister for the Next and Back Button
        nNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if the user in the role selection page
                if (nCurrentPage == 0){
                    //get the user role type form the spinner
                    user_type = nUserType.getSelectedItem().toString();
                    //display toast message
                    Toast.makeText(getApplicationContext(),user_type +" "+"Selected",Toast.LENGTH_LONG).show();
                    //increase the page counter
                    mSlideViewPager.setCurrentItem(nCurrentPage + 1);
                }else if (nCurrentPage == 1){  //if the user in the input username page
                    first_name = nfNameField.getText().toString();
                    last_name = nlNameField.getText().toString();
                    mSlideViewPager.setCurrentItem(nCurrentPage + 1);
                }else if (nCurrentPage == 2){  //if the user in the input address page
                    city = ncityField.getText().toString();
                    state = nStateField.getText().toString();
                    mSlideViewPager.setCurrentItem(nCurrentPage + 1);
                } else if (nCurrentPage == 3) //if the user in the final questionnaire page
                {
                    phone = nphonefield.getText().toString();

                    if ( user_type.equals("Student")) {
                        if(FirebaseAuth.getInstance().getCurrentUser() != null){
                            String userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                            StudentDao student = new StudentDaoImpl();
                            student.createStudent(userEmail, first_name, last_name, phone, city, state, null, null);
                        }
                        else{
                            Log.e(TAG, "Logged in user instance not found");
                        }
                    }else if (user_type.equals("Mentor")) {
                        if(FirebaseAuth.getInstance().getCurrentUser() != null){
                            String userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                            MentorDao mentor = new MentorDaoImpl();
                            mentor.createMentor(userEmail, first_name, last_name, phone, city, state, null, null);
                        }
                        else {
                            Log.e(TAG, "Logged in user instance not found");
                        }
                    }
                   //Toast.makeText(getApplicationContext(),user_type + " Created",Toast.LENGTH_LONG).show(); //display toast message
                    finish();
                }}

        });

        nBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSlideViewPager.setCurrentItem(nCurrentPage - 1);  //get back to the previoyus page when back button is clicked
            }
        });

    }

    //adding Dot Indicator to the Dot Layout
    public void addDotsIndicator(int position){
        mDots = new TextView[4];
        mDotLayout.removeAllViews();

        for (int i = 0; i < mDots.length; i++){

            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.end));
            mDotLayout.addView(mDots[i]);
        }
        //changes the color of the dots of the corresponding page
        if (mDots.length > 0){
            mDots[position].setTextColor(getResources().getColor(R.color.White));

        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
            nCurrentPage = position;

            //sets the widgets enabled and disabled with theior visibilty based on the position of the dots or the pagecounter
            if (position == 0){
                nNextButton.setEnabled(true);
                nBackButton.setEnabled(false);

                nUserType.setEnabled(true);
                nfNameField.setEnabled(false);
                nlNameField.setEnabled(false);
                ncityField.setEnabled(false);
                nStateField.setEnabled(false);
                nphonefield.setEnabled(false);

                nBackButton.setVisibility(View.INVISIBLE);
                nUserType.setVisibility(View.VISIBLE);
                nfNameField.setVisibility(View.INVISIBLE);
                nlNameField.setVisibility(View.INVISIBLE);
                ncityField.setVisibility(View.INVISIBLE);
                nStateField.setVisibility(View.INVISIBLE);
                nphonefield.setVisibility(View.INVISIBLE);

                nNextButton.setText("Next");
                nBackButton.setText("");

            }else if (position == mDots.length - 1){
                nNextButton.setEnabled(true);
                nBackButton.setEnabled(true);

                nUserType.setEnabled(false);
                nfNameField.setEnabled(false);
                nlNameField.setEnabled(false);
                ncityField.setEnabled(false);
                nStateField.setEnabled(false);
                nphonefield.setEnabled(true);

                nBackButton.setVisibility(View.VISIBLE);

                nUserType.setVisibility(View.INVISIBLE);
                nfNameField.setVisibility(View.INVISIBLE);
                nlNameField.setVisibility(View.INVISIBLE);
                ncityField.setVisibility(View.INVISIBLE);
                nStateField.setVisibility(View.INVISIBLE);
                nphonefield.setVisibility(View.VISIBLE);

                nNextButton.setText("Finish");
                nBackButton.setText("Back");

            }else if (position == 1){
                nNextButton.setEnabled(true);
                nBackButton.setEnabled(true);

                nUserType.setEnabled(false);
                nfNameField.setEnabled(true);
                nlNameField.setEnabled(true);
                ncityField.setEnabled(false);
                nStateField.setEnabled(false);
                nphonefield.setEnabled(false);


                nUserType.setVisibility(View.INVISIBLE);
                nfNameField.setVisibility(View.VISIBLE);
                nlNameField.setVisibility(View.VISIBLE);
                ncityField.setVisibility(View.INVISIBLE);
                nStateField.setVisibility(View.INVISIBLE);
                nphonefield.setVisibility(View.INVISIBLE);
                nBackButton.setVisibility(View.VISIBLE);

                nNextButton.setText("Next");
                nBackButton.setText("Back");
            }else{
                nNextButton.setEnabled(true);
                nBackButton.setEnabled(true);

                nUserType.setEnabled(false);
                nfNameField.setEnabled(false);
                nlNameField.setEnabled(false);
                ncityField.setEnabled(true);
                nStateField.setEnabled(true);
                nphonefield.setEnabled(false);

                nUserType.setVisibility(View.INVISIBLE);
                nfNameField.setVisibility(View.INVISIBLE);
                nlNameField.setVisibility(View.INVISIBLE);
                ncityField.setVisibility(View.VISIBLE);
                nStateField.setVisibility(View.VISIBLE);
                nphonefield.setVisibility(View.INVISIBLE);

                nNextButton.setText("Next");
                nBackButton.setText("Back");
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}
