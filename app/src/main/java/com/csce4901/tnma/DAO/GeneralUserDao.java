package com.csce4901.tnma.DAO;

import android.view.MenuItem;

import java.util.concurrent.ExecutionException;

public interface GeneralUserDao {
    public void createGeneralUser(String email);
    public void enableRegisterForGeneralUser(String email, MenuItem menuItem);
}
