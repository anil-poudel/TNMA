package com.csce4901.tnma.DAO.Impl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.csce4901.tnma.Connector.FirebaseConnector;
import com.csce4901.tnma.DAO.BlogDao;
import com.csce4901.tnma.Models.Blog;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.csce4901.tnma.Constants.UserConstant.FS_BLOGS_COLLECTION;
import static com.csce4901.tnma.Constants.UserConstant.FS_BLOGS_USER_COMMENTS;
import static com.csce4901.tnma.Constants.UserConstant.IS_FEATURED;

public class BlogDaoImpl implements BlogDao {
    FirebaseConnector fbConnector = new FirebaseConnector();

    @Override
    public void createNewBlog(String title, String post, String image, String author) {
        Blog newBlog = new Blog(title, post, image, author);
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
                    Map<String, String> existingUserComment = blog.getComments();
                    existingUserComment.put(userEmail, userComment);
                    db.collection(FS_BLOGS_COLLECTION)
                            .document(blogTitle)
                            .set(blog, SetOptions.mergeFields(FS_BLOGS_USER_COMMENTS));
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
            if(task.isSuccessful()) {
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
                if(f_blog_img_URL == null || f_blog_img_URL.isEmpty()){
                } else {
                    try {
                        bitmap = BitmapFactory.decodeStream((InputStream)new URL(f_blog_img_URL).getContent());
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
}
