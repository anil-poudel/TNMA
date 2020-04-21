package com.csce4901.tnma.Models;

import java.util.Map;

public class CommentModel {
    String author;
    String cDt;
    String cText;

    public CommentModel() { }

    public CommentModel(String author, String cDt, String cText){
        this.author = author;
        this.cDt = cDt;
        this.cText = cText;
    }

    public String getAuthor() {return author;}

    public void setAuthor(String author){this.author = author;}

    public String getcDt() {return cDt;}

    public void setcDt(String cDt){this.cDt = cDt;}

    public String getcText() {return cText;}

    public void setcText(String cText){this.cText = cText;}
}