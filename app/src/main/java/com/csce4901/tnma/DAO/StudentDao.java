package com.csce4901.tnma.DAO;

public interface StudentDao {
    public void createStudent(String email, String fname, String lname, String phone, String city, String state,
                              String school, String address);
}
