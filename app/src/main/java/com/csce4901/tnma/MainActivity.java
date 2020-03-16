package com.csce4901.tnma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.csce4901.tnma.Connector.FirebaseConnector;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements LoginTab.OnFragmentInteractionListener, SignupTab.OnFragmentInteractionListener{

    private static Context context;
    ImageView tnmaLogo;
    FirebaseConnector fbConnector = new FirebaseConnector();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.context = getApplicationContext();
        setContentView(R.layout.activity_main);
        //set logo dynamically
        retrieveDynamicLogoFromDB();

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Login"));
        tabLayout.addTab(tabLayout.newTab().setText("Signup"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager)findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
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

        }

    public static Context getAppContext() {
        return MainActivity.context;
    }

    protected void retrieveDynamicLogoFromDB(){
        tnmaLogo = findViewById(R.id.tnmaLogo);
        fbConnector.firebaseSetup();
        FirebaseFirestore db = fbConnector.getDb();
        DocumentReference docRef = db.collection("dynamic-logo").document("TNMA_LOGO");
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Log.d(MainActivity.class.getName(), "DocumentSnapshot data: " + document.getData());
                    Picasso.get()
                            .load(document.get("logo").toString())
                            .resize(158,158)
                            .into(tnmaLogo);
                } else {
                    Log.d(MainActivity.class.getName(), "No such document");
                }
            } else {
                Log.d(MainActivity.class.getName(), "get failed with ", task.getException());
            }
        });
    }
}

