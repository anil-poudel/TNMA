package com.csce4901.tnma;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.csce4901.tnma.DAO.GeneralUserDao;
import com.csce4901.tnma.DAO.Impl.GeneralUserDaoImpl;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileInfoPopOut extends AppCompatActivity {
    private TextView profilefName;
    private TextView profilelName;
    private TextView profileName;
    private TextView profilePhone;
    private TextView profileCity;
    private TextView profileState;
    private ImageView avatar;
    private TextView eclose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_info_pop_out);
        profilefName = findViewById(R.id.fName);
        profilelName = findViewById(R.id.lName);
        profileName = findViewById(R.id.name);
        profilePhone = findViewById(R.id.phoneNum);
        profileCity = findViewById(R.id.city);
        profileState = findViewById(R.id.state);
        avatar = findViewById(R.id.staticAvatar);
        eclose = findViewById(R.id.txt_close);
        eclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        GeneralUserDao user = new GeneralUserDaoImpl();
        user.getUserProfileInfo2(email, profilefName, profilelName, profileName, profilePhone, profileCity, profileState);

        Drawable img = getApplicationContext().getResources().getDrawable(R.drawable.tnma);
        avatar.setImageDrawable(img);
    }
}
