package com.csce4901.tnma.DAO.Impl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.csce4901.tnma.BlogAdapter;
import com.csce4901.tnma.Connector.FirebaseConnector;
import com.csce4901.tnma.DAO.BlogDao;
import com.csce4901.tnma.Models.Blog;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.csce4901.tnma.Constants.UserConstant.FS_BLOGS_COLLECTION;
import static com.csce4901.tnma.Constants.UserConstant.FS_BLOGS_USER_COMMENTS;
import static com.csce4901.tnma.Constants.UserConstant.FS_BLOGS_USER_COMMENT_COUNT;
import static com.csce4901.tnma.Constants.UserConstant.FS_BLOG_BOOST_COUNT;
import static com.csce4901.tnma.Constants.UserConstant.FS_BLOG_BOOST_USERS;
import static com.csce4901.tnma.Constants.UserConstant.IS_FEATURED;

public class BlogDaoImpl implements BlogDao {
    FirebaseConnector fbConnector = new FirebaseConnector();

    @Override
    public void createNewBlog(String title, String post, String image, String author, Integer boostCount, Integer commentCount) {
        Blog newBlog = new Blog(title, post, image, author, 0, 0);
        fbConnector.firebaseSetup();
        FirebaseFirestore db = fbConnector.getDb();
        db.collection(FS_BLOGS_COLLECTION).document(title).set(newBlog)
                .addOnSuccessListener(aVoid -> {
                    Log.i(TAG, "Blog detail stored in database for: " + title);
                })
                .addOnFailureListener(aVoid -> {
                    Log.e(TAG, "Unable to store blog detail for: " + title);
                });
    }


    @Override
    public void addUserCommentToBlog(String userEmail, String userComment, String blogTitle) {
        fbConnector.firebaseSetup();
        FirebaseFirestore db = fbConnector.getDb();
        DocumentReference docRef = db.collection(FS_BLOGS_COLLECTION).document(blogTitle);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                assert document != null;
                if (document.exists()) {
                    Blog blog = document.toObject(Blog.class);
                    Map<String, Map<String, String>> existingUserComment = Objects.requireNonNull(blog).getComments();
                    Map<String, String> commentorList = existingUserComment.get(userEmail);
                    String currentTime = new Date().toString();
                    //If userEmail has no previous records, create new one.
                    if(commentorList == null)
                    {
                        commentorList = new HashMap<>();
                    }
                    commentorList.put(currentTime, userComment);
                    existingUserComment.put(userEmail, commentorList);

                    db.collection(FS_BLOGS_COLLECTION)
                            .document(blogTitle)
                            .set(blog, SetOptions.mergeFields(FS_BLOGS_USER_COMMENTS));

                    //update count as well
                    int count = blog.getCommentCount();
                    count++;
                    db.collection(FS_BLOGS_COLLECTION)
                            .document(blogTitle)
                            .update(FS_BLOGS_USER_COMMENT_COUNT, count);
                } else {
                    Log.e(TAG, "Blog does not exist: " + blogTitle);
                }
            } else {
                Log.e(TAG, "get failed with ", task.getException());
            }
        });
    }

    @Override
    public void getFeaturedBlog(TextView title, TextView desc, ImageView image) {
        fbConnector.firebaseSetup();
        FirebaseFirestore db = fbConnector.getDb();
        Query featuredEventQuery = db.collection(FS_BLOGS_COLLECTION).whereEqualTo(IS_FEATURED, true).limit(1);
        featuredEventQuery.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String f_blog_title = "No featured blog";
                String f_blog_desc = "No featured blog";
                String f_blog_img_URL = null;
                Bitmap bitmap = null;
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Blog featuredBlog = document.toObject(Blog.class);
                    f_blog_title = featuredBlog.getTitle();
                    f_blog_desc = featuredBlog.getPost();
                    f_blog_img_URL = featuredBlog.getImageURL();
                }
                title.setText(f_blog_title);
                desc.setText(f_blog_desc);
                if (f_blog_img_URL == null || f_blog_img_URL.isEmpty()) {
                } else {
                    try {
                        bitmap = BitmapFactory.decodeStream((InputStream) new URL(f_blog_img_URL).getContent());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    image.setImageBitmap(bitmap);
                }
            } else {
                Log.e(TAG, "Failed to get featured blog");
            }
        });
    }

    @Override
    public void getAllBlogs(String userEmail, RecyclerView recyclerView, FragmentActivity fragmentActivity) {
        fbConnector.firebaseSetup();
        FirebaseFirestore db = fbConnector.getDb();
        db.collection(FS_BLOGS_COLLECTION)
                .orderBy("dt", Query.Direction.DESCENDING)
                .addSnapshotListener((value, e) -> {
                    if (e != null) {
                        Log.w(TAG, "Listen failed.", e);
                        return;
                    }

                    List<String> title_list = new LinkedList<>();
                    List<String> author_list = new LinkedList<>();
                    List<String> date_list = new LinkedList<>();
                    List<String> desc_list = new LinkedList<>();
                    List<String> img_list = new LinkedList<>();
                    List<Integer> boostSize_list = new LinkedList<>();
                    List<Integer> commentSize_list = new LinkedList<>();
                    List<Boolean> hasBoosted_list = new LinkedList<>();

                    for (QueryDocumentSnapshot document : value) {
                        Blog blog = document.toObject(Blog.class);
                        title_list.add(blog.getTitle());
                        author_list.add(blog.getAuthor());
                        date_list.add(blog.getDt().toString());
                        desc_list.add(blog.getPost());
                        img_list.add(blog.getImageURL());

                        //Checking for empty map
                        if(blog.getBoostedByUser() != null) {
                            boostSize_list.add(blog.getBoostCount());
                            hasBoosted_list.add((blog.getBoostedByUser().get(userEmail)));
                        }
                        else
                        {
                            boostSize_list.add(0);
                            hasBoosted_list.add(false);
                        }
                        commentSize_list.add(blog.getCommentCount());
                    }

                    String[] post_title = title_list.toArray(new String[0]);
                    String[] post_author = author_list.toArray(new String[0]);
                    String[] post_date = date_list.toArray(new String[0]);
                    String[] post_desc = desc_list.toArray(new String[0]);
                    String[] post_img = img_list.toArray(new String[0]);
                    Integer[] post_boost = boostSize_list.toArray(new Integer[0]);
                    Integer[] post_comment = commentSize_list.toArray(new Integer[0]);
                    Boolean[] post_hasBoosted = hasBoosted_list.toArray(new Boolean[0]);

                    BlogAdapter blogAdapter = new BlogAdapter(fragmentActivity,
                            post_title, post_author, post_date, post_desc,
                            post_img, post_boost, post_comment, post_hasBoosted);

                    recyclerView.setAdapter(blogAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(fragmentActivity));
                });
    }


    @Override
    public void boostCount(String blogTitle, int boostCount, String userEmail, Boolean userBoost) {
        fbConnector.firebaseSetup();
        FirebaseFirestore db = fbConnector.getDb();
        DocumentReference docRef = db.collection(FS_BLOGS_COLLECTION).document(blogTitle);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                assert document != null;
                if (document.exists()) {
                    Blog blog = document.toObject(Blog.class);
                    Map<String, Boolean> existingBoost = Objects.requireNonNull(blog).getBoostedByUser();
                    existingBoost.put(userEmail,userBoost);
                        db.collection(FS_BLOGS_COLLECTION)
                            .document(blogTitle)
                            .set(blog, SetOptions.mergeFields(FS_BLOG_BOOST_USERS));

                    //update boostCount
                    db.collection(FS_BLOGS_COLLECTION)
                            .document(blogTitle)
                            .update(FS_BLOG_BOOST_COUNT, boostCount);
                } else {
                    Log.e(TAG, "Blog does not exist: " + blogTitle);
                }
            } else {
                Log.e(TAG, "get failed with ", task.getException());
            }
        });
    }

    @Override
    public void getAllComments(String blogTitle, RecyclerView recyclerView)
    {

    }
}