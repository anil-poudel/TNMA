package com.csce4901.tnma.DAO.Impl;

import android.util.Log;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

import com.csce4901.tnma.Connector.FirebaseConnector;
import com.csce4901.tnma.DAO.QuestionDao;
import com.csce4901.tnma.Models.Question;
import com.google.firebase.firestore.FirebaseFirestore;

import static androidx.constraintlayout.widget.Constraints.TAG;

import static com.csce4901.tnma.Constants.UserConstant.FS_QUESTIONS_COLLECTION;

public class QuestionDaoImpl implements QuestionDao {
    private FirebaseConnector fbConnector = new FirebaseConnector();


    @Override
    public void addQuestion(String email, String question, Boolean isAnswered, List<String> answers, List<String> answeredBy) {
        String timeStamp = String.valueOf(new Date().getTime());
        String questionKey = email + " " + timeStamp;
        Question newQuestion = new Question(question, false, null, null);
        fbConnector.firebaseSetup();
        FirebaseFirestore db = fbConnector.getDb();

        db.collection(FS_QUESTIONS_COLLECTION).document(questionKey)
                .set(newQuestion)
                .addOnSuccessListener(aVoid -> {
                    Log.i(TAG, "Question detail stored in database for: " + question);
                })
                .addOnFailureListener(aVoid -> {
                    Log.e(TAG, "Unable to store question detail for: " + question);
                });
    }
}

