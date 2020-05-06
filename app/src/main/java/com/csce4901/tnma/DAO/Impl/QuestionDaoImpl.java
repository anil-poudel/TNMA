package com.csce4901.tnma.DAO.Impl;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.csce4901.tnma.Connector.FirebaseConnector;
import com.csce4901.tnma.DAO.QuestionDao;
import com.csce4901.tnma.MainActivity;
import com.csce4901.tnma.Models.GeneralUser;
import com.csce4901.tnma.Models.Question;
import com.csce4901.tnma.Models.User;
import com.csce4901.tnma.QNA_Adapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import static androidx.constraintlayout.widget.Constraints.TAG;

import static com.csce4901.tnma.Constants.UserConstant.ADMIN_ROLE;
import static com.csce4901.tnma.Constants.UserConstant.FS_QUESTIONS_ANSWER;
import static com.csce4901.tnma.Constants.UserConstant.FS_QUESTIONS_ANSWERER;
import static com.csce4901.tnma.Constants.UserConstant.FS_QUESTIONS_COLLECTION;
import static com.csce4901.tnma.Constants.UserConstant.FS_QUESTIONS_ISANSWERED;
import static com.csce4901.tnma.Constants.UserConstant.FS_QUESTIONS_QUESTION;
import static com.csce4901.tnma.Constants.UserConstant.FS_USERS_COLLECTION;
import static com.csce4901.tnma.Constants.UserConstant.MENTOR_ROLE;
import static com.csce4901.tnma.Constants.UserConstant.STUDENT_ROLE;

public class QuestionDaoImpl implements QuestionDao {
    private FirebaseConnector fbConnector = new FirebaseConnector();

    int[] role = new int[1];

    @Override
    public void addQuestion(String email, String question, Boolean isAnswered, String answer, String answeredBy) {
        String timeStamp = (new Date().toString());
        String questionKey = email + " " + timeStamp;
        Question newQuestion = new Question(email, question, false, "", "");
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

    @Override
    public void getUserQuestions(String email, RecyclerView qnaRecycler, FragmentActivity fragmentActivity, Context context) {
        fbConnector.firebaseSetup();
        FirebaseFirestore db = fbConnector.getDb();
        //Get user role
        DocumentReference docRef = db.collection(FS_USERS_COLLECTION).document(email);
        role[0] = 0;

        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    GeneralUser generalUser = document.toObject(GeneralUser.class);
                    role[0] = generalUser.getRole();

                    //For student, view their old questions
                    if (role[0] == STUDENT_ROLE) {
                        db.collection(FS_QUESTIONS_COLLECTION)
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            List<String> answers_list = new LinkedList<>();
                                            List<String> answeredBy_list = new LinkedList<>();
                                            List<String> question_list = new LinkedList<>();

                                            for (QueryDocumentSnapshot qsnap : task.getResult()) {
                                                Question question = qsnap.toObject(Question.class);
                                                if (qsnap.getId().contains(email)) {
                                                    if (!(question.isAnswered())) {
                                                        question_list.add(question.getQuestion());
                                                        answers_list.add("This question has not been answered yet.");
                                                        answeredBy_list.add("n/a");
                                                    } else {
                                                        question_list.add(question.getQuestion());
                                                        answers_list.add(question.getAnswer());
                                                        answeredBy_list.add(question.getAnsweredBy());
                                                    }
                                                }
                                            }
                                            String[] questions = question_list.toArray(new String[0]);
                                            String[] answers = answers_list.toArray(new String[0]);
                                            String[] answeredBy = answeredBy_list.toArray(new String[0]);
                                            QNA_Adapter adapter = new QNA_Adapter(context, questions, answers, answeredBy);
                                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
                                            qnaRecycler.setLayoutManager(layoutManager);
                                            qnaRecycler.setAdapter(adapter);
                                        }
                                    }
                                });
                    }
                    //For mentors, show all questions.
                    if (role[0] == MENTOR_ROLE) {
                        db.collection(FS_QUESTIONS_COLLECTION)
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            List<String> answers_list = new LinkedList<>();
                                            List<String> answeredBy_list = new LinkedList<>();
                                            List<String> questions_list = new LinkedList<>();

                                            for (QueryDocumentSnapshot qsnap : task.getResult()) {
                                                Question question = qsnap.toObject(Question.class);
                                                if (!(question.isAnswered())) {
                                                    questions_list.add(question.getQuestion());
                                                    answers_list.add("This question has not been answered yet.");
                                                    answeredBy_list.add("n/a");

                                                } else {
                                                    questions_list.add(question.getQuestion());
                                                    answers_list.add(question.getAnswer());
                                                    answeredBy_list.add(question.getAnsweredBy());
                                                }
                                            }

                                            String[] questions = questions_list.toArray(new String[0]);
                                            String[] answers = answers_list.toArray(new String[0]);
                                            String[] answeredBy = answeredBy_list.toArray(new String[0]);
                                            QNA_Adapter adapter = new QNA_Adapter(context, questions, answers, answeredBy);
                                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
                                            qnaRecycler.setLayoutManager(layoutManager);
                                            qnaRecycler.setAdapter(adapter);
                                        }
                                    }
                                });
                    }

                }
            }
        });
    }

    @Override
    public void setMentorAnswer(String question, String mentorAnswer) {
        fbConnector.firebaseSetup();
        FirebaseFirestore db = fbConnector.getDb();
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        Query findQuestion = db.collection(FS_QUESTIONS_COLLECTION).whereEqualTo(FS_QUESTIONS_QUESTION, question).limit(1);
        findQuestion.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                    String askedBy;
                    String[] arr;
                    arr = documentSnapshot.getId().split(" ");
                    askedBy = arr[0];
                    Log.d(TAG, "asked by: " + askedBy);

                    //Set answer
                    db.collection(FS_QUESTIONS_COLLECTION)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    for (QueryDocumentSnapshot querySnapshot : task.getResult()) {
                                        String docName;
                                        Question question1 = querySnapshot.toObject(Question.class);
                                        if (question1.getAsker().equals(askedBy)) {
                                            docName = querySnapshot.getId();
                                            if (!question1.isAnswered()) {
                                                db.collection(FS_QUESTIONS_COLLECTION)
                                                        .document(docName)
                                                        .update(FS_QUESTIONS_ANSWER, mentorAnswer);
                                                db.collection(FS_QUESTIONS_COLLECTION)
                                                        .document(docName)
                                                        .update(FS_QUESTIONS_ANSWERER, email);
                                                db.collection(FS_QUESTIONS_COLLECTION)
                                                        .document(docName)
                                                        .update(FS_QUESTIONS_ISANSWERED, true);
                                            }
                                        }
                                    }
                                }
                            });
                }

            } else {
                Log.e(TAG, "Failed to find the question.");
            }
        });
    }

    @Override
    public void manageVisibility(String email, Button btn) {
        fbConnector.firebaseSetup();
        FirebaseFirestore db = fbConnector.getDb();
        //Get user role
        DocumentReference docRef = db.collection(FS_USERS_COLLECTION).document(email);
        role[0] = 0;

        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    GeneralUser generalUser = document.toObject(GeneralUser.class);
                    role[0] = generalUser.getRole();

                    if (role[0] == MENTOR_ROLE) {
                        btn.setVisibility(View.VISIBLE);
                    }

                }
            }
        });
    }

    @Override
    public void getAllQuestionsListView(String email, ListView listView, Context ctx) {
        fbConnector.firebaseSetup();
        FirebaseFirestore db = fbConnector.getDb();
        // Check current user role
        DocumentReference docRef = db.collection(FS_USERS_COLLECTION).document(email);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                assert document != null;
                if (document.exists()) {
                    User generalUser = document.toObject(GeneralUser.class);
                    int currUserRole = generalUser.getRole();
                    // Display questions asked by current user only
                    if(currUserRole == STUDENT_ROLE){
                        db.collection(FS_QUESTIONS_COLLECTION)
                                .get()
                                .addOnCompleteListener(t1 -> {
                                    if (t1.isSuccessful()) {
                                        List<String> question_list_1 = new LinkedList<>();
                                        for (QueryDocumentSnapshot doc1 : t1.getResult()) {
                                            Question question = doc1.toObject(Question.class);
                                            if(question.getAsker().equals(email)){
                                                question_list_1.add(question.getQuestion());
                                            }
                                        }
                                        String[] question_title = question_list_1.toArray(new String[0]);
                                        ArrayAdapter arrayAdapter = new ArrayAdapter(ctx,android.R.layout.simple_list_item_1,question_title);
                                        listView.setAdapter(arrayAdapter);
                                    } else {
                                        Log.e(TAG, "Error getting documents: ", t1.getException());
                                    }
                                });
                        // Display all questions for Mentor/Admin role
                    } else if (currUserRole == MENTOR_ROLE || currUserRole == ADMIN_ROLE) {
                        db.collection(FS_QUESTIONS_COLLECTION)
                                .get()
                                .addOnCompleteListener(t2 -> {
                                    if (t2.isSuccessful()) {
                                        List<String> question_list_2 = new LinkedList<>();
                                        for (QueryDocumentSnapshot doc2 : t2.getResult()) {
                                            Question question = doc2.toObject(Question.class);
                                            question_list_2.add(question.getQuestion());
                                        }
                                        String[] question_title = question_list_2.toArray(new String[0]);
                                        ArrayAdapter arrayAdapter = new ArrayAdapter(ctx,android.R.layout.simple_list_item_1,question_title);
                                        listView.setAdapter(arrayAdapter);
                                    } else {
                                        Log.e(TAG, "Error getting documents: ", t2.getException());
                                    }
                                });
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
