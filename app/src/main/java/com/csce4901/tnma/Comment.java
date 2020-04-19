package com.csce4901.tnma;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.csce4901.tnma.Connector.FirebaseConnector;
import com.csce4901.tnma.DAO.BlogDao;
import com.csce4901.tnma.DAO.Impl.BlogDaoImpl;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class Comment extends AppCompatActivity {

    String userComment;
    String titleComment;
    BlogDao blogDao1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        //Get blogTitle from blogAdapter
        if(getIntent().hasExtra("title"))
        {
            titleComment = getIntent().getStringExtra("title");
        }
        else
        {
            Toast.makeText(this,"Could not find postTitle. Database linking errror.", Toast.LENGTH_SHORT).show();
        }

        blogDao1 = new BlogDaoImpl();
        Button postBtn = (Button) findViewById(R.id.postCommentButton);
        EditText commentText = (EditText) findViewById(R.id.addCommentText);
        String email = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();


        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userComment = commentText.getText().toString();
                blogDao1.addUserCommentToBlog(email, userComment, titleComment);
                Toast.makeText(v.getContext(), "Comment successfully posted.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
