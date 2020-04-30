package com.csce4901.tnma.Models;

import java.util.Date;
import java.util.List;

public class Question {
    String question;
    List<String> answers;
    List<String> answeredBy;
    Boolean isAnswered = false;

    public Question(){ }
    public Question( String question, Boolean isAnswered, List<String> answeredBy, List<String> answer) {
        this.question = question;
        this.answers = answer;
        this.answeredBy = answeredBy;
        this.isAnswered = isAnswered;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAnswer() {
        return answers;
    }

    public void setAnswer(List<String> answer) {
        this.answers = answer;
    }

    public List<String> getAnsweredBy() {
        return answeredBy;
    }

    public void setAnsweredBy(List<String> answeredBy) {
        this.answeredBy = answeredBy;
    }

    public boolean isAnswered() {
        return isAnswered;
    }

    public void setAnswered(boolean answered) {
        isAnswered = answered;
    }
}
