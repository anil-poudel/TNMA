package com.csce4901.tnma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggleDrawer;
    Fragment selectedfragment;

    private ViewPager viewPager;
    private DashboardSwipeAdapter adapter;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        viewPager = findViewById(R.id.dashboardPager);
        adapter = new DashboardSwipeAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        drawerLayout = findViewById(R.id.sideDrawer);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationView);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        toggleDrawer = new ActionBarDrawerToggle(Dashboard.this, drawerLayout, toolbar, R.string.drawerOpen, R.string.drawerClose);
        drawerLayout.addDrawerListener(toggleDrawer);
        toggleDrawer.syncState();

        //TODO:Set home as default fragment
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
                Toast.makeText(Dashboard.this, "News Clicked", Toast.LENGTH_LONG).show();
               viewPager.setCurrentItem(0);
            }
        });

        //TODO: goto blogFragment
        LinearLayout blogBottomNav = findViewById(R.id.blogButton);
        blogBottomNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Dashboard.this, "Blog Clicked", Toast.LENGTH_LONG).show();
              viewPager.setCurrentItem(2);
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId())
        {
            case (R.id.profileMenu):
                Toast.makeText(this, "TODO: Profile", Toast.LENGTH_SHORT).show();
                break;
            case (R.id.contactMenu):
                Toast.makeText(Dashboard.this, "TODO: Contact", Toast.LENGTH_SHORT).show();
                break;
            case (R.id.donateMenu):
                Toast.makeText(Dashboard.this, "TODO: Donate", Toast.LENGTH_SHORT).show();
                break;
            case (R.id.logoutMenu):
                Toast.makeText(Dashboard.this, "TODO: LogOut", Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }
}
