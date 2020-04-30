package com.csce4901.tnma.DAO.Impl;

import android.util.Log;

import com.csce4901.tnma.Connector.FirebaseConnector;
import com.csce4901.tnma.DAO.ChatDao;
import com.csce4901.tnma.MainActivity;
import com.csce4901.tnma.Models.Chat;
import com.csce4901.tnma.Models.GeneralUser;
import com.csce4901.tnma.Models.Mentor;
import com.csce4901.tnma.Models.Student;
import com.csce4901.tnma.Models.User;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.csce4901.tnma.Constants.UserConstant.FS_CHATS_COLLECTION;
import static com.csce4901.tnma.Constants.UserConstant.FS_USERS_COLLECTION;
import static com.csce4901.tnma.Constants.UserConstant.MENTOR_ROLE;
import static com.csce4901.tnma.Constants.UserConstant.STUDENT_ROLE;

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

    @Override
    public void retrieveMessages(String email) {
        fbConnector.firebaseSetup();
        FirebaseFirestore db = fbConnector.getDb();
        // get current user detail to load chat data
        db.collection(FS_USERS_COLLECTION).document(email).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                assert document != null;
                if (document.exists()) {
                    User generalUser = document.toObject(GeneralUser.class);
                    if(generalUser.getRole() == STUDENT_ROLE){
                        Student student = document.toObject(Student.class);
                        String stuMentorEmail = student.getMentor();
                        db.collection(FS_CHATS_COLLECTION)
                                .get()
                                .addOnCompleteListener(t1 -> {
                                    if (t1.isSuccessful()) {
                                        // querying the whole chat
                                        List<Chat> chatList = null;
                                        for (QueryDocumentSnapshot doc1 : t1.getResult()) {
                                            if(doc1.exists()){
                                                Chat queriedChat = doc1.toObject(Chat.class);
                                                if(queriedChat.getSender() == email && queriedChat.getReceiver() == stuMentorEmail){
                                                    chatList.add(queriedChat);
                                                }
                                            }
                                        }
                                        // TO DO: <RABI>
                                        // at this point all message from logged in student to
                                        // his mentor will be stored in "chatList"
                                    } else {
                                        Log.e(TAG, "Error getting documents: ", task.getException());
                                    }
                                });
                    }
                    if(generalUser.getRole() == MENTOR_ROLE){
                        Mentor mentor = document.toObject(Mentor.class);
                        List<String> stuListForMentor = mentor.getStudents();
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
