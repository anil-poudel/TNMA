package com.csce4901.tnma.Models;

import java.util.Date;

public class Chat {
    String sender;
    String receiver;
    String message;
    String dt;

    public Chat(){
    }

    public Chat(String publisher, String consumer, String message){
        this.sender = publisher;
        this.receiver = consumer;
        this.message = message;
        this.dt = new Date().toString();
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
