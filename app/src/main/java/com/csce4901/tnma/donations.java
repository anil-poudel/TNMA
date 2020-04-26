package com.csce4901.tnma;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.csce4901.tnma.DAO.DonationsDao;
import com.csce4901.tnma.DAO.Impl.DonationsDaoImpl;

public class donations extends AppCompatActivity {

    ListView listView;
    String donations[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donations);

        listView = findViewById(R.id.list_donations);

        // gets array from string.xml- replace this from values in database
        // "donation + amount" for each string in array- concatenate strings
        DonationsDao donationsDao = new DonationsDaoImpl();
        donationsDao.getDonations(listView, this);
    }
}