package com.csce4901.tnma.Models;

public class Chat {
    String sender;
    String receiver;
    String message;

    public Chat(){
    }

    public Chat(String publisher, String consumer, String message){
        this.sender = publisher;
        this.receiver = consumer;
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
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
