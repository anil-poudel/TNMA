package com.csce4901.tnma.DAO;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public interface BlogDao {
    public void createNewBlog(String title, String post, String image, String author, Integer boostCount, Integer commentCount);
    public void addUserCommentToBlog(String userEmail, String userComment, String blogTitle);
    public void getFeaturedBlog(TextView title, TextView desc, ImageView image);

    //retrieve all blogs
    public void getAllBlogs(String userEmail, RecyclerView recyclerView, FragmentActivity fragmentActivity);
    //Boost a blog post
    public  void boostCount(String blogTitle, int boostCount, String userEmail, Boolean userBoost);
}



