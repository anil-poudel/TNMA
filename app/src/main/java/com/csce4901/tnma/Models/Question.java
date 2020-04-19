package com.csce4901.tnma.Models;
import java.util.Date;

public class Question {
    private Date dt;
    private String question;
    private String answer;
    private String answeredBy;
    boolean isAnswered = false;

    public Question(){ }
    public Question(String question) {
        this.question = question;
        this.dt = new Date();
        this.answer = null;
        this.answeredBy = null;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnsweredBy() {
        return answeredBy;
    }

    public void setAnsweredBy(String answeredBy) {
        this.answeredBy = answeredBy;
    }

    public boolean isAnswered() {
        return isAnswered;
    }

    public void setAnswered(boolean answered) {
        isAnswered = answered;
    }
}
