package com.csce4901.tnma;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {

    int numTabs;

    public PagerAdapter (FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numTabs = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {

            case 0:
                LoginTab logintab = new LoginTab();
                return logintab;
            case 1:
                SignupTab signuptab = new SignupTab();
                return signuptab;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numTabs;
    }
}
