package com.csce4901.tnma.DAO;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public interface GeneralUserDao {
    public void createGeneralUser(String email);
    public void disableVerifiedMemberFeature(String email, MenuItem menuItem, Menu menu);
    public void enableBtnForGuestUser(String email, Button button);
    }
