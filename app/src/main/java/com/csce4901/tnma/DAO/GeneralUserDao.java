package com.csce4901.tnma.DAO;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public interface GeneralUserDao {
    public void createGeneralUser(String email);
    public void manageVisibilityForGuestUsrFeature(String email, Menu menu, Button btn, MenuItem profileMenuItem);
    public void getUserProfileInfo(String email, TextView profileName, TextView profilePhone, TextView profileRole);
    }
