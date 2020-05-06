package com.csce4901.tnma.DAO.Impl;

import android.util.Log;

import com.csce4901.tnma.Connector.FirebaseConnector;
import com.csce4901.tnma.DAO.ChatDao;
import com.csce4901.tnma.Models.Chat;
import com.google.firebase.firestore.FirebaseFirestore;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.csce4901.tnma.Constants.UserConstant.FS_CHATS_COLLECTION;

public class ChatDaoImpl implements ChatDao {
    FirebaseConnector fbConnector = new FirebaseConnector();

    @Override
    public void storeNewMessage(String dt, String sender, String receiver, String message) {
        fbConnector.firebaseSetup();
        FirebaseFirestore db = fbConnector.getDb();
        Chat chat = new Chat(sender, receiver, message);
        db.collection(FS_CHATS_COLLECTION).document().set(chat)
                .addOnSuccessListener(aVoid -> {
                    Log.i(TAG, "Chat detail stored in database");
                })
                .addOnFailureListener(aVoid -> {
                    Log.e(TAG, "Unable to store message");
                });
    }
}
