package com.csce4901.tnma.DAO.Impl;

import android.util.Log;
import java.util.Date;

import com.csce4901.tnma.Connector.FirebaseConnector;
import com.csce4901.tnma.DAO.QuestionDao;
import com.csce4901.tnma.Models.Question;
import com.google.firebase.firestore.FirebaseFirestore;

import static androidx.constraintlayout.widget.Constraints.TAG;

import static com.csce4901.tnma.Constants.UserConstant.FS_QUESTIONS_COLLECTION;

public class QuestionDaoImpl implements QuestionDao {
    FirebaseConnector fbConnector = new FirebaseConnector();

    public void addQuestion(String question) {
        Question newQuestion = new Question(question);
        fbConnector.firebaseSetup();
        FirebaseFirestore db = fbConnector.getDb();
        db.collection(FS_QUESTIONS_COLLECTION).document(question).set(newQuestion)
                .addOnSuccessListener(aVoid -> {
                    Log.i(TAG, "Question detail stored in database for: " + question);
                })
                .addOnFailureListener(aVoid -> {
                    Log.e(TAG, "Unable to store question detail for: " + question);
                });
    }


}
