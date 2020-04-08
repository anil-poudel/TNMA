package com.csce4901.tnma.DAO;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public interface GeneralUserDao {
    public void createGeneralUser(String email);
    public void manageVisibilityForGuestUsrFeature(String email, MenuItem menuItem, Menu menu, Button button);
    }
