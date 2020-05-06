package com.csce4901.tnma.DAO;

import android.content.Context;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public interface QuestionDao {
    //add question
    public void addQuestion(String email, String question, Boolean isAnswered, String answers, String answeredBy);
    //get user's questions
    public void getUserQuestions(String email, RecyclerView qnaRecycler, FragmentActivity fragmentActivity, Context context);
    //add answer to question
    public void setMentorAnswer(String question, String mentorAnswer);
    //manage visibility of buttons
    public void manageVisibility(String email, Button btn);
    //get list of questions for profile
    public void getAllQuestionsListView(String email, ListView listView, Context ctx);
}
