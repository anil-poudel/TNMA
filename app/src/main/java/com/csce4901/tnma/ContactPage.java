package com.csce4901.tnma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ContactPage extends AppCompatActivity {
    TextView inputName;
    TextView inputEmail;
    TextView inputPhone;
    TextView inputComment;
    Button Submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_page);

        inputEmail = (TextView) findViewById(R.id.contactEmail);
        inputPhone = (TextView) findViewById(R.id.contactPhone);
        inputComment = (TextView) findViewById(R.id.contactMessage);
        Submit = (Button) findViewById(R.id.contactButton);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String to = ((TextView)findViewById(R.id.contactEmail)).getText().toString();
                String sub = ((TextView)findViewById(R.id.contactSubject)).getText().toString();
                String mess1 = ((TextView)findViewById(R.id.contactMessage)).getText().toString();
                String mess2 = ((TextView)findViewById(R.id.contactPhone)).getText().toString();
                String mess = mess1 + "\n\nI can be reached at: " + mess2;

                Intent mail = new Intent(Intent.ACTION_SEND);
                mail.putExtra(Intent.EXTRA_EMAIL,new String[]{to});
                mail.putExtra(Intent.EXTRA_SUBJECT, sub);
                mail.putExtra(Intent.EXTRA_TEXT, mess);
                mail.setType("message/rfc822");
                startActivity(Intent.createChooser(mail, "Send email via:"));
            }
        });
    }
}
