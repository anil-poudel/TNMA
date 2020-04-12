package com.csce4901.tnma;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class EventPopUp extends AppCompatActivity {

    ImageView eimage;
    TextView etitle,edesc,eclose,edate,etime,eaddress;
    Button eBtn;

    String data1,data2,data3,data4,data5;
    int images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_pop_up);

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
        eBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Event Joined", Toast.LENGTH_LONG).show();
            }
        });
        getdata();
        setData();
    }
    private void getdata(){
    if (getIntent().hasExtra("images") && getIntent().hasExtra("data1") && getIntent().hasExtra("data2")&& getIntent().hasExtra("data3")&& getIntent().hasExtra("data4")&& getIntent().hasExtra("data5")){

        data1 = getIntent().getStringExtra("data1");
        data2 = getIntent().getStringExtra("data2");
        data3 = getIntent().getStringExtra("data3");
        data4 = getIntent().getStringExtra("data4");
        data5 = getIntent().getStringExtra("data5");
        images = getIntent().getIntExtra("images",1);

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
        eimage.setImageResource(images);
    }
}
