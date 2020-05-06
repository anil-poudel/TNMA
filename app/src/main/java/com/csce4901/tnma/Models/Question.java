package com.csce4901.tnma.Models;

import java.util.Date;
import java.util.List;

public class Question {
    String asker;
    String question;
    String answer;
    String answeredBy;
    Boolean isAnswered = false;

    public Question(){ }
    public Question( String asker, String question, Boolean isAnswered, String answeredBy, String answer) {
        this.asker = asker;
        this.question = question;
        this.answer = answer;
        this.answeredBy = answeredBy;
        this.isAnswered = isAnswered;
    }

    public String getAsker(){ return  asker;}

    public void setAsker(){this.asker = asker;}

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

    public String getAnsweredBy() {return answeredBy;}

    public void setAnsweredBy(String  answeredBy) {
        this.answeredBy = answeredBy;
    }

    public boolean isAnswered() {
        return isAnswered;
    }

    public void setAnswered(Boolean answered) {
        isAnswered = answered;
    }
}
