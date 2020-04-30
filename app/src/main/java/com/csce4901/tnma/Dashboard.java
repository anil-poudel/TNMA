package com.csce4901.tnma;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.csce4901.tnma.DAO.BlogDao;
import com.csce4901.tnma.DAO.GeneralUserDao;
import com.csce4901.tnma.DAO.Impl.BlogDaoImpl;
import com.csce4901.tnma.DAO.Impl.GeneralUserDaoImpl;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    //Items
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggleDrawer;
    private ViewPager viewPager;
    Snackbar snackbar;
    Dialog questionDialog;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        viewPager = findViewById(R.id.dashboardPager);
        DashboardSwipeAdapter adapter = new DashboardSwipeAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        viewPager.setCurrentItem(1);

        //Set home as default fragment
        viewPager.setCurrentItem(1);

        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.quickaction_menu);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        drawerLayout = findViewById(R.id.sideDrawer);
        toggleDrawer = new ActionBarDrawerToggle(Dashboard.this, drawerLayout, toolbar, R.string.drawerOpen, R.string.drawerClose);
        drawerLayout.addDrawerListener(toggleDrawer);
        toggleDrawer.syncState();

        //Set email on Drawer
        View headerView = navigationView.getHeaderView(0);
        TextView drawerEmail = headerView.findViewById(R.id.userEmailDrawer);

        //Setup visibility of menu items in navigation drawer based on roles
        Menu drawer_menu = navigationView.getMenu();
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            MenuItem profileUserItem = drawer_menu.findItem(R.id.profileMenu);
            GeneralUserDao generalUser = new GeneralUserDaoImpl();
            String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
            drawerEmail.setText(email);
            generalUser.manageVisibilityForGuestUsrFeature(email, null, null, profileUserItem);
        }

        FloatingActionButton homeBottomNav = findViewById(R.id.homeButton);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if(position==1 || position==3)
                {
                    homeBottomNav.setImageResource(R.drawable.ic_ask);
                }
                else
                {
                    homeBottomNav.setImageResource(R.drawable.ic_home);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        homeBottomNav.setOnClickListener(v -> {
            questionDialog = new Dialog(v.getContext());
            questionDialog.setContentView(R.layout.popup_question);
            Button btn = questionDialog.findViewById(R.id.ask_questionBtn);
            Button previous = questionDialog.findViewById(R.id.view_questionsBtn);

            if(viewPager.getCurrentItem()==1 || viewPager.getCurrentItem()==3) {
                questionDialog.show();

                btn.setOnClickListener(v1 -> questionDialog.dismiss());
                previous.setOnClickListener(v12 -> {
                    viewPager.setCurrentItem(3);
                    questionDialog.dismiss();
                });
            }
            homeBottomNav.setImageResource(R.drawable.ic_ask);
            viewPager.setCurrentItem(1);
        });

        LinearLayout newsBottomNav = findViewById(R.id.newsButton);
        newsBottomNav.setOnClickListener(v -> viewPager.setCurrentItem(0));


        LinearLayout blogBottomNav = findViewById(R.id.blogButton);
        blogBottomNav.setOnClickListener(v -> viewPager.setCurrentItem(2));

        //Double click back to logout
        snackbar = Snackbar.make(drawerLayout, "Please press Back again to Logout.", Snackbar.LENGTH_SHORT);

    }


    @Override
    public boolean onNavigationItemSelected( MenuItem menuItem) {
        switch (menuItem.getItemId())
        {
            case (R.id.profileMenu):
                Toast.makeText(Dashboard.this, "Profile Opened.", Toast.LENGTH_SHORT).show();
                //If not first login, goto dashboard
                Intent intent
                        = new Intent(this,
                        Profile.class);
                startActivity(intent);
                break;
            case (R.id.contactMenu):
                Toast.makeText(Dashboard.this, "Contact Page Opened.", Toast.LENGTH_SHORT).show();
                Intent intent2
                        = new Intent(this,
                        ContactPage.class);
                startActivity(intent2);
                break;
            case (R.id.donateMenu):
                Toast.makeText(Dashboard.this, "Donation Page Opened.", Toast.LENGTH_SHORT).show();
                Intent intent3
                        = new Intent(this,
                        donations.class);
                startActivity(intent3);
                break;
            case (R.id.logoutMenu):
                FirebaseAuth.getInstance().signOut();
                finish();
                break;
        }
        return true;
    }

    //Setup Actionbar with Quick Actions depending on User Roles
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.quickaction_menu, menu);
        //For Guest
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            GeneralUserDao generalUser = new GeneralUserDaoImpl();
            generalUser.manageVisibilityForGuestUsrFeature(FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                    menu, null, null);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId())
        {
            case (R.id.ask_Action):
                //questionDialog.show();
                //viewPager.setCurrentItem(3);
                break;

            case (R.id.message_Action):
                Toast.makeText(Dashboard.this, "TODO: Direct Messaging", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    //Double press back button to exit
    @Override
    public void onBackPressed() {
        if(snackbar.isShown())
        {
            FirebaseAuth.getInstance().signOut();
            super.onBackPressed();
        }else {
            snackbar.show();
        }
    }
}
