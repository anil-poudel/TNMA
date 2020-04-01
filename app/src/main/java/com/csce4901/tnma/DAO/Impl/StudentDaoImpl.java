package com.csce4901.tnma.DAO.Impl;

import android.util.Log;

import com.csce4901.tnma.Connector.FirebaseConnector;
import com.csce4901.tnma.DAO.StudentDao;
import com.csce4901.tnma.Models.Student;
import com.csce4901.tnma.Models.User;
import com.google.firebase.firestore.FirebaseFirestore;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class StudentDaoImpl implements StudentDao {
    FirebaseConnector fbConnector = new FirebaseConnector();

    @Override
    public void createStudent(String email, String fname, String lname, String phone, String city,
                              String state, String school, String address) {
        User student = new Student(email, fname, lname, phone, city, state);
        fbConnector.firebaseSetup();
        FirebaseFirestore db = fbConnector.getDb();
        db.collection("users").document(email).set(student)
                .addOnSuccessListener(aVoid -> {
                    Log.i(TAG, "User detail stored in database for: " + email);
                })
                .addOnFailureListener(aVoid -> {
                    Log.e(TAG, "Unable to store user detail for: " + email);
                });
    }
}
