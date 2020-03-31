package com.csce4901.tnma.DAO.Impl;

import android.util.Log;

import com.csce4901.tnma.Connector.FirebaseConnector;
import com.csce4901.tnma.models.Student;
import com.csce4901.tnma.models.User;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class StudentDaoImplTest {

    public void addStudentTest (){
        FirebaseConnector fbConnector = new FirebaseConnector();
        fbConnector.firebaseSetup();
        fbConnector.firebaseSetup();
        User student = new Student("abiral7@gmail.com", "abiral", "pandey",
                "(469) 426-7946", "Euless", "TX");
        // Add a new document with a generated ID
        fbConnector.getDb().collection("users")
                .document("abiral7@gmail.com")
                .set(student)
                .addOnSuccessListener(e -> Log.d(TAG, "DocumentSnapshot added with ID"))
                .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));
    }
}
