package com.csce4901.tnma.DAO;

public interface BlogDao {
    public void createNewBlog(String title, String post, String image, String author);
    public void addUserCommentToBlog(String userEmail, String userComment, String blogTitle);
    }
