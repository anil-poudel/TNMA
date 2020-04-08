package com.csce4901.tnma.DAO.Impl;

import android.util.Log;

import com.csce4901.tnma.Connector.FirebaseConnector;
import com.csce4901.tnma.DAO.MentorDao;
import com.csce4901.tnma.Models.Mentor;
import com.csce4901.tnma.Models.User;
import com.google.firebase.firestore.FirebaseFirestore;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MentorDaoImpl implements MentorDao {
    FirebaseConnector fbConnector = new FirebaseConnector();

    @Override
    public void createMentor(String email, String fname, String lname, String phone, String city,
                              String state, String school, String address) {
        User mentor = new Mentor(email, fname, lname, phone, city, state);
        fbConnector.firebaseSetup();
        FirebaseFirestore db = fbConnector.getDb();
        db.collection("users").document(email).set(mentor)
                .addOnSuccessListener(aVoid -> {
                    Log.i(TAG, "Mentor detail stored in database for: " + email);
                })
                .addOnFailureListener(aVoid -> {
                    Log.e(TAG, "Unable to store mentor detail for: " + email);
                });
    }
}
