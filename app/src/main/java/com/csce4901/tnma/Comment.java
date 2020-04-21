package com.csce4901.tnma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.csce4901.tnma.DAO.BlogDao;
import com.csce4901.tnma.DAO.Impl.BlogDaoImpl;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class Comment extends AppCompatActivity {

    String userComment;
    String titleComment;
    BlogDao blogDao1;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        //Get blogTitle from blogAdapter
        if(getIntent().hasExtra("title"))
        {
            titleComment = getIntent().getStringExtra("title");
        } else {
            Toast.makeText(this,"Could not find postTitle. Database linking errror.",
                    Toast.LENGTH_SHORT).show();
        }

        blogDao1 = new BlogDaoImpl();
        Button postBtn = findViewById(R.id.postCommentButton);
        EditText commentText = findViewById(R.id.addCommentText);
        String email = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();

        recyclerView = findViewById(R.id.commentRecyclerView);
        BlogDao blogDao = new BlogDaoImpl();
        blogDao.getAllComments(titleComment, recyclerView, this);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);



        postBtn.setOnClickListener(v -> {
            userComment = commentText.getText().toString();
            blogDao1.addUserCommentToBlog(email, userComment, titleComment);
            Toast.makeText(v.getContext(), "Comment successfully posted.",
                    Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
