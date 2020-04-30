package com.csce4901.tnma.DAO;

import java.util.List;

public interface QuestionDao {
    //add question
    public void addQuestion(String email, String question, Boolean isAnswered, List<String> answers, List<String> answeredBy);
    //get all questions

    //add answer to question
    //get unanswered questions
    //get answered questions
}
