package com.csce4901.tnma.DAO;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public interface BlogDao {
    public void createNewBlog(String title, String post, String image, String author);
    public void addUserCommentToBlog(String userEmail, String userComment, String blogTitle);
    public void getFeaturedBlog(TextView title, TextView desc, ImageView image);

    //retrieve all blogs
    public void getAllBlogs(RecyclerView recyclerView, FragmentActivity fragmentActivity);
    public  void boostCount(String email, String blogTitle, int count);
    }
