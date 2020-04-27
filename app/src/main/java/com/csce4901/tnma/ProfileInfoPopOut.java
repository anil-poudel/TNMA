package com.csce4901.tnma;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    private Button changeInfo;
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
        changeInfo = findViewById(R.id.btnChangeInfo);

        eclose.setOnClickListener(v -> finish());
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        GeneralUserDao user = new GeneralUserDaoImpl();
        user.getUserProfileEditInfo(email, profilefName, profilelName, profileName, profilePhone, profileCity, profileState);
        Drawable img = getApplicationContext().getResources().getDrawable(R.drawable.tnma);
        avatar.setImageDrawable(img);

        changeInfo.setOnClickListener(v -> {
            GeneralUserDao generalUserDao = new GeneralUserDaoImpl();
            if(isNotEmpty(profilefName) && isNotEmpty(profilelName) && isNotEmpty(profilePhone)
                    && isNotEmpty(profileCity) && isNotEmpty(profileState)) {
                generalUserDao.updateUserProfileInfo(email, profilefName.getText().toString(),
                        profilelName.getText().toString(),
                        profilePhone.getText().toString(),
                        profileCity.getText().toString(),
                        profileState.getText().toString());
                Toast.makeText(getBaseContext(), "User info updated successfully", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getBaseContext(), "Cannot update empty field", Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean isNotEmpty(TextView etText) {
        if (etText.getText().toString().trim().length() > 0)
            return true;
        return false;
    }
}
