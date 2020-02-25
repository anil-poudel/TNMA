package com.csce4901.tnma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity{

//    Variables For Signup Transitions
//    TextView txt1;
//    EditText edit1, edit2, edit3;
//    Button btn1, btn2, btn3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Login"));
        tabLayout.addTab(tabLayout.newTab().setText("Signup"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager)findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

//        //Transitions for Signup tab
//        btn1 = (Button)findViewById(R.id.confirm_email);
//        btn2 = (Button)findViewById(R.id.verify);
//        btn3 = (Button)findViewById(R.id.create_account);
//
//        txt1 = (TextView)findViewById(R.id.text001);
//        edit1 = (EditText)findViewById(R.id.verification);
//        edit2 = (EditText)findViewById(R.id.passcode1);
//        edit3 = (EditText)findViewById(R.id.passcode2);
//        //
//
//        btn1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//              txt1.setVisibility(View.VISIBLE);
//              edit1.setVisibility(View.VISIBLE);
//              btn2.setVisibility(View.VISIBLE);
//              Toast.makeText(getApplicationContext(),"A verification code has been sent" +
//                        "to your email address.", Toast.LENGTH_LONG).show();
//            }
//        });
//

        }
}

