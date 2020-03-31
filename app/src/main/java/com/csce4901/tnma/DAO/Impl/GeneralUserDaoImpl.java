package com.csce4901.tnma.DAO.Impl;

import android.util.Log;

import com.csce4901.tnma.Connector.FirebaseConnector;
import com.csce4901.tnma.DAO.GeneralUserDao;
import com.csce4901.tnma.models.GeneralUser;
import com.csce4901.tnma.models.User;
import com.google.firebase.firestore.FirebaseFirestore;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class GeneralUserDaoImpl implements GeneralUserDao {
    FirebaseConnector fbConnector = new FirebaseConnector();

    @Override
    public void createGeneralUser(String email) {
        User generalUser = new GeneralUser(email, true);
        fbConnector.firebaseSetup();
        FirebaseFirestore db = fbConnector.getDb();
        db.collection("users").document(email).set(generalUser)
                .addOnSuccessListener(aVoid -> {
                    Log.i(TAG, "User detail stored in database for: " + email);
                })
                .addOnFailureListener(aVoid -> {
                    Log.e(TAG, "Unable to store user detail for: " + email);
                });
    }
}
