package com.csce4901.tnma;

import android.util.Log;

import com.csce4901.tnma.Connector.FirebaseConnector;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class FirebaseTest {

    FirebaseConnector fbConnector = new FirebaseConnector();
    public void addUser (){
        fbConnector.firebaseSetup();
        Map<String, Object> user = new HashMap<>();
        user.put("fname", "firstname");
        user.put("lname", "lastname");
        user.put("email", "user@gmail.com");

        // Add a new document with a generated ID
        fbConnector.getDb().collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }
}
