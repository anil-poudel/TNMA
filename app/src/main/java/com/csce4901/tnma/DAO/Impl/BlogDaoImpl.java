package com.csce4901.tnma.DAO.Impl;

import android.util.Log;

import com.csce4901.tnma.Connector.FirebaseConnector;
import com.csce4901.tnma.DAO.BlogDao;
import com.csce4901.tnma.Models.Blog;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.csce4901.tnma.Constants.UserConstant.FS_BLOGS_COLLECTION;
import static com.csce4901.tnma.Constants.UserConstant.FS_BLOGS_USER_COMMENTS;

public class BlogDaoImpl implements BlogDao {
    FirebaseConnector fbConnector = new FirebaseConnector();

    @Override
    public void createNewBlog(String title, String post) {
        Blog newBlog = new Blog(title, post);
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
}
