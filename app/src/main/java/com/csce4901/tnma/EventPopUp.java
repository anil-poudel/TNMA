package com.csce4901.tnma;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.csce4901.tnma.DAO.EventDao;
import com.csce4901.tnma.DAO.Impl.EventDaoImpl;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

public class EventPopUp extends AppCompatActivity {

    ImageView eimage;
    TextView etitle,edesc,eclose,edate,etime,eaddress;
    Button eBtn;

    String data1,data2,data3,data4,data5;
    String images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_pop_up);
        EventDao eventDao = new EventDaoImpl();
        String curr_user_email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        eimage = findViewById(R.id.event_image);
        etitle = findViewById(R.id.event_title);
        edesc = findViewById(R.id.event_desc);
        eclose = findViewById(R.id.txt_close);
        edate = findViewById(R.id.event_date);
        etime = findViewById(R.id.event_time);
        eaddress = findViewById(R.id.event_address);
        eBtn = findViewById(R.id.btn_join);
        eclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if(eBtn != null){
            eBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(FirebaseAuth.getInstance().getCurrentUser() != null){
                        eventDao.addUserToEvent(curr_user_email, data1);
                        Toast.makeText(getBaseContext(), "Event Joined", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getBaseContext(), "Unable to join", Toast.LENGTH_LONG).show();
                    }
                    finish();
                }
            });
        }
        getdata();
        setData();
        eventDao.manageEventBtnVisibility(eBtn, curr_user_email, data1);
    }
    private void getdata(){
    if (getIntent().hasExtra("images") && getIntent().hasExtra("data1") && getIntent().hasExtra("data2")&& getIntent().hasExtra("data3")&& getIntent().hasExtra("data4")&& getIntent().hasExtra("data5")){

        data1 = getIntent().getStringExtra("data1");
        data2 = getIntent().getStringExtra("data2");
        data3 = getIntent().getStringExtra("data3");
        data4 = getIntent().getStringExtra("data4");
        data5 = getIntent().getStringExtra("data5");
        images = getIntent().getStringExtra("images");

    }else{
        Toast.makeText(this,"No data found",Toast.LENGTH_SHORT).show();
    }
    }

    private void setData(){
        etitle.setText(data1);
        edesc.setText(data2);
        eaddress.setText(data3);
        etime.setText(data4);
        edate.setText(data5);
        Picasso.get()
                .load(images)
                .resize(500,500)
                .centerCrop(Gravity.START)
                .into(eimage);

    }
}
