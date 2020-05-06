package com.csce4901.tnma;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.csce4901.tnma.DAO.Impl.QuestionDaoImpl;
import com.csce4901.tnma.DAO.QuestionDao;
import com.google.firebase.auth.FirebaseAuth;

public class questions extends AppCompatActivity {

    ListView listView;
    String questions[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        listView = findViewById(R.id.list_questions);
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        QuestionDao questionDao = new QuestionDaoImpl();
        questionDao.getAllQuestionsListView(email, listView, this);
    }
}
