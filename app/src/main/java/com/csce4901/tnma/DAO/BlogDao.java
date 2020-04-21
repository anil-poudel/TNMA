package com.csce4901.tnma.DAO;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.csce4901.tnma.Comment;

public interface BlogDao {
    //create a new blog post
    public void createNewBlog(String title, String post, String image, String author, Integer boostCount, Integer commentCount);
    //add comments to the blog post
    public void addUserCommentToBlog(String userEmail, String userComment, String blogTitle);
    //get featured blog post to display in Homepage
    public void getFeaturedBlog(TextView title, TextView desc, ImageView image);
    //retrieve all blogs
    public void getAllBlogs(String userEmail, RecyclerView recyclerView, FragmentActivity fragmentActivity);
    //Boost a blog post
    public  void boostCount(String blogTitle, int boostCount, String userEmail, Boolean userBoost);
    //retrieve all comments to a blog post
    public void getAllComments(String blogTitle, RecyclerView recyclerView, Context context);

}



