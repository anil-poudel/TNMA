package com.csce4901.tnma;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class DashboardSwipeAdapter extends FragmentStatePagerAdapter {

    public DashboardSwipeAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                NewsFeed newsFeed = new NewsFeed();
                return newsFeed;
            case 1:
                Home home = new Home();
                return home;
            case 2:
                Blogs blogs = new Blogs();
                return blogs;
            case 3:
                QNA qna = new QNA();
                return qna;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
