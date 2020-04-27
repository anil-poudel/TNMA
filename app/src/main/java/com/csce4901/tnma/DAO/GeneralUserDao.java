package com.csce4901.tnma.DAO;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public interface GeneralUserDao {
    public void createGeneralUser(String email);
    public void manageVisibilityForGuestUsrFeature(String email, Menu menu, Button btn, MenuItem profileMenuItem);
    public void getUserProfileInfo(String email, TextView profileName, TextView profilePhone, TextView profileRole);
    //For profile pop-up information card. Not an ideal approach, but I could not get this to work for string parameters.
    public void getUserProfileEditInfo(String email, TextView editfName, TextView editlName, TextView editName, TextView editPhone, TextView editCity, TextView editState);
    public void updateUserProfileInfo(String email, String fname, String lname, String phone, String city, String state);
    }
