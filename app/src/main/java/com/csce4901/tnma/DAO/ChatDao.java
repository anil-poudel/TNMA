package com.csce4901.tnma.DAO;

public interface ChatDao {
    public void storeNewMessage(String sender, String receiver, String message);
    public void retrieveMessages(String email);
}
