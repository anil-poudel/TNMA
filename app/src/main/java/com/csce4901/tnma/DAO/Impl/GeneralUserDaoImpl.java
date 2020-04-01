package com.csce4901.tnma.DAO.Impl;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.csce4901.tnma.Connector.FirebaseConnector;
import com.csce4901.tnma.Constants.UserConstant;
import com.csce4901.tnma.DAO.GeneralUserDao;
import com.csce4901.tnma.MainActivity;
import com.csce4901.tnma.Models.GeneralUser;
import com.csce4901.tnma.Models.User;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
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

    @Override
    public void disableVerifiedMemberFeature(String email, MenuItem registerMenu, Menu menu){
        fbConnector.firebaseSetup();
        FirebaseFirestore db = fbConnector.getDb();
        DocumentReference docRef = db.collection("users").document(email);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                assert document != null;
                if (document.exists()) {
                    User generalUser = document.toObject(GeneralUser.class);
                    if(generalUser.getRole() != UserConstant.GENERAL_USER_ROLE){
                        Log.i(TAG, email + " : Not a general user.");
                        // disable register option in menu for general user
                        if(registerMenu != null)
                            registerMenu.setVisible(false);
                    }
                    else {
                        // disable ask a question & messaging for general user
                        if(menu != null){
                            menu.getItem(0).setVisible(false);
                            menu.getItem(1).setVisible(false);
                        }
                    }
                } else {
                    Log.d(MainActivity.class.getName(), "No such document with email " + email);
                }
            } else {
                Log.d(MainActivity.class.getName(), "get failed with ", task.getException());
            }
        });
    }
}
