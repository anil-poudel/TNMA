package com.csce4901.tnma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.csce4901.tnma.Connector.FirebaseConnector;
import com.csce4901.tnma.DAO.ChatDao;
import com.csce4901.tnma.DAO.Impl.ChatDaoImpl;
import com.csce4901.tnma.Models.Chat;
import com.csce4901.tnma.chat_adapter.chattingAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.csce4901.tnma.Constants.UserConstant.FS_CHATS_COLLECTION;

public class chatting extends AppCompatActivity {
    TextView username;
    Intent intent;
    String mUsers = null;

    ImageButton btn_send;
    EditText text_send;
    private final String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();


    chattingAdapter chattingadapter;
    List<Chat> mchat;


    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);



        username = findViewById(R.id.username);
        btn_send = findViewById(R.id.btn_send);
        text_send = findViewById(R.id.text_send);
        intent = getIntent();



        getdata();
        setData();

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = text_send.getText().toString();
                String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

                if (!msg.isEmpty()) {
                    ChatDao chat = new ChatDaoImpl();
                    String timeStamp = new Date().toString();
                    chat.storeNewMessage(timeStamp, email, mUsers, msg);
                } else {
                    Toast.makeText(chatting.this, "You can't send empty message", Toast.LENGTH_SHORT).show();
                }
                text_send.setText("");
            }
        });

    }

    private void getdata() {
        if (intent.hasExtra("mUsers")) {
            mUsers = getIntent().getStringExtra("mUsers");
        } else {
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        }

        readMessage(mUsers);
    }

    private void setData() {
        username.setText(mUsers);

    }

    private void readMessage(String otheruser) {
        mchat = new ArrayList<>();
        FirebaseConnector fbconnector = new FirebaseConnector();
        fbconnector.firebaseSetup();
        FirebaseFirestore db = fbconnector.getDb();

        db.collection(FS_CHATS_COLLECTION)
                .orderBy("dt", Query.Direction.ASCENDING)
                .addSnapshotListener((value, e) ->{
                    if(e!=null)
                    {
                        Log.w("Listen failed.", e);
                    }
                    mchat.clear();
                    for(QueryDocumentSnapshot documentSnapshots : value)
                    {
                        Chat chat = documentSnapshots.toObject(Chat.class);
                        if (chat.getReceiver().equals(email) && chat.getSender().equals(otheruser) ||
                                chat.getReceiver().equals(otheruser) && chat.getSender().equals(email)) {
                            mchat.add(chat);
                        }
                        chattingadapter = new chattingAdapter(chatting.this, mchat);
                        recyclerView.setAdapter(chattingadapter);
                    };
                });
    }
}








