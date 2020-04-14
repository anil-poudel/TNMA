package com.csce4901.tnma.DAO;

public interface BlogDao {
    public void createNewBlog(String title, String post);
    public void addUserCommentToBlog(String userEmail, String userComment, String blogTitle);
    }
