package com.csce4901.tnma.DAO;

import android.content.Context;
import android.widget.Button;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public interface QuestionDao {
    //add question
    public void addQuestion(String email, String question, Boolean isAnswered, String answers, String answeredBy);
    //get user's questions
    public void getUserQuestions(String email, RecyclerView qnaRecycler, FragmentActivity fragmentActivity, Context context);
    //add answer to question
    public void setMentorAnswer(String question, String mentorAnswer);
    //get unanswered questions
    //get answered questions

    //manage visibility of buttons
    public void manageVisibility(String email, Button btn);
}
