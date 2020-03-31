package com.csce4901.tnma;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.csce4901.tnma.DAO.GeneralUserDao;
import com.csce4901.tnma.DAO.Impl.GeneralUserDaoImpl;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;


public class Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    //Items
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggleDrawer;
    private ViewPager viewPager;
    Snackbar snackbar;

    //User Role
    private int role = 1;

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

        //Setup visibility of menu items in navigation drawer based on roles
        GeneralUserDao user = new GeneralUserDaoImpl();
        Menu drawer_menu = navigationView.getMenu();
        MenuItem registerUserItem = drawer_menu.findItem(R.id.registerMenu);
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        user.disableRegisterOptionCheck(email, registerUserItem);

        FloatingActionButton homeBottomNav = findViewById(R.id.homeButton);
        homeBottomNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Dashboard.this,"Home Clicked", Toast.LENGTH_SHORT).show();
               viewPager.setCurrentItem(1);

            }
        });

        //TODO: goto news Fragment
        LinearLayout newsBottomNav = findViewById(R.id.newsButton);
        newsBottomNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Dashboard.this, "News Clicked", Toast.LENGTH_SHORT).show();
               viewPager.setCurrentItem(0);
            }
        });

        //TODO: goto blogFragment
        LinearLayout blogBottomNav = findViewById(R.id.blogButton);
        blogBottomNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Dashboard.this, "Blog Clicked", Toast.LENGTH_SHORT).show();
              viewPager.setCurrentItem(2);
            }
        });

        //Double click back to logout
        snackbar = Snackbar.make(drawerLayout, "Please press Back again to Logout.", Snackbar.LENGTH_SHORT);

    }


    @Override
    public boolean onNavigationItemSelected( MenuItem menuItem) {
        switch (menuItem.getItemId())
        {
            case (R.id.profileMenu):
                Toast.makeText(Dashboard.this, "TODO: Profile", Toast.LENGTH_SHORT).show();
                break;
            case (R.id.contactMenu):
                Toast.makeText(Dashboard.this, "TODO: Contact", Toast.LENGTH_SHORT).show();
                break;
            case (R.id.donateMenu):
                Toast.makeText(Dashboard.this, "TODO: Donate", Toast.LENGTH_SHORT).show();
                break;
            case (R.id.registerMenu):
                Toast.makeText(Dashboard.this, "TODO: Register for Events", Toast.LENGTH_SHORT).show();
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
        if (role == 0)
        {
                menu.getItem(0).setVisible(false);
                menu.getItem(1).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId())
        {
            case (R.id.ask_Action):
                Toast.makeText(Dashboard.this, "TODO: Ask a Question", Toast.LENGTH_SHORT).show();
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
