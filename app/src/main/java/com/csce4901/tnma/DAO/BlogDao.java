package com.csce4901.tnma.DAO;

import android.widget.ImageView;
import android.widget.TextView;

public interface BlogDao {
    public void createNewBlog(String title, String post, String image, String author);
    public void addUserCommentToBlog(String userEmail, String userComment, String blogTitle);
    public void getFeaturedBlog(TextView title, TextView desc, ImageView image);
    }
