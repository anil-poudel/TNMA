package com.csce4901.tnma.DAO;

public interface ChatDao {
    public void storeNewMessage(String dt, String sender, String receiver, String message);
}
