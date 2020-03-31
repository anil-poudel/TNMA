package com.csce4901.tnma.DAO.Impl;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.csce4901.tnma.Connector.FirebaseConnector;
import com.csce4901.tnma.Constants.UserConstant;
import com.csce4901.tnma.DAO.GeneralUserDao;
import com.csce4901.tnma.MainActivity;
import com.csce4901.tnma.R;
import com.csce4901.tnma.models.GeneralUser;
import com.csce4901.tnma.models.User;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

import androidx.drawerlayout.widget.DrawerLayout;

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
    public void enableRegisterForGeneralUser(String email, MenuItem registerMenu){
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
                        registerMenu.setVisible(false);
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
