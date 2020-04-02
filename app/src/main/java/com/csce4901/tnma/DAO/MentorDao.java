package com.csce4901.tnma.DAO;

public interface MentorDao {
    public void createMentor(String email, String fname, String lname, String phone, String city, String state,
                              String school, String address);
}
