package com.csce4901.tnma.Connector;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

public class FirebaseConnector {

    private FirebaseFirestore db;

    public FirebaseFirestore getDb() {
        return db;
    }

    public void firebaseSetup(){
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
    }
}
