package com.csce4901.tnma.DAO.Impl;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.csce4901.tnma.Connector.FirebaseConnector;
import com.csce4901.tnma.DAO.DonationsDao;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.LinkedList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.csce4901.tnma.Constants.UserConstant.FS_DONATIONS_COLLECTION;
import static com.csce4901.tnma.Constants.UserConstant.FS_DONATIONS_DETAILS;

public class DonationsDaoImpl implements DonationsDao {
    FirebaseConnector fbConnector = new FirebaseConnector();

    @Override
    public void getDonations(ListView listView, Context ctx) {
        fbConnector.firebaseSetup();
        FirebaseFirestore db = fbConnector.getDb();
        db.collection(FS_DONATIONS_COLLECTION)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<String> donation_list = new LinkedList<>();

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String detail = (String) document.get(FS_DONATIONS_DETAILS);
                            donation_list.add(detail);
                        }
                        String[] donation_title = donation_list.toArray(new String[0]);
                        ArrayAdapter arrayAdapter = new ArrayAdapter(ctx,android.R.layout.simple_list_item_1,donation_title);
                        listView.setAdapter(arrayAdapter);
                    } else {
                        Log.e(TAG, "Error getting documents: ", task.getException());
                    }
                });
    }
}
