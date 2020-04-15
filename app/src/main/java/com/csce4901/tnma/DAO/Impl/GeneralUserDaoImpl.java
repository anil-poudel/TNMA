package com.csce4901.tnma.DAO.Impl;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.csce4901.tnma.Connector.FirebaseConnector;
import com.csce4901.tnma.Constants.UserConstant;
import com.csce4901.tnma.DAO.GeneralUserDao;
import com.csce4901.tnma.MainActivity;
import com.csce4901.tnma.Models.GeneralUser;
import com.csce4901.tnma.Models.Mentor;
import com.csce4901.tnma.Models.Student;
import com.csce4901.tnma.Models.User;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.csce4901.tnma.Constants.UserConstant.FS_USERS_COLLECTION;
import static com.csce4901.tnma.Constants.UserConstant.IS_MENTOR;
import static com.csce4901.tnma.Constants.UserConstant.IS_STUDENT;
import static com.csce4901.tnma.Constants.UserConstant.MENTOR_ROLE;
import static com.csce4901.tnma.Constants.UserConstant.STUDENT_ROLE;

public class GeneralUserDaoImpl implements GeneralUserDao {
    FirebaseConnector fbConnector = new FirebaseConnector();

    @Override
    public void createGeneralUser(String email) {
        User generalUser = new GeneralUser(email, true);
        fbConnector.firebaseSetup();
        FirebaseFirestore db = fbConnector.getDb();
        db.collection(FS_USERS_COLLECTION).document(email).set(generalUser)
                .addOnSuccessListener(aVoid -> {
                    Log.i(TAG, "User detail stored in database for: " + email);
                })
                .addOnFailureListener(aVoid -> {
                    Log.e(TAG, "Unable to store user detail for: " + email);
                });
    }

    @Override
    public void getUserProfileInfo(String email, TextView profileName, TextView profilePhone, TextView profileRole){
        fbConnector.firebaseSetup();
        FirebaseFirestore db = fbConnector.getDb();
        DocumentReference docRef = db.collection(FS_USERS_COLLECTION).document(email);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                assert document != null;
                if (document.exists()) {
                    StringBuilder name = null;
                    String phone = null;
                    User generalUser = document.toObject(GeneralUser.class);
                    if(generalUser.getRole() == STUDENT_ROLE){
                        Student student = document.toObject(Student.class);
                        name = new StringBuilder()
                                .append(student.getFname())
                                .append(" ")
                                .append(student.getLname());
                        phone = student.getPhone();
                        profileRole.setText(IS_STUDENT);
                    }
                    if(generalUser.getRole() == MENTOR_ROLE){
                        Mentor mentor = document.toObject(Mentor.class);
                        name = new StringBuilder()
                                .append(mentor.getFname())
                                .append(" ")
                                .append(mentor.getLname());
                        phone = mentor.getPhone();
                        profileRole.setText(IS_MENTOR);
                    }
                    profileName.setText(name);
                    profilePhone.setText(phone);
                } else {
                    Log.d(MainActivity.class.getName(), "No such document with email " + email);
                }
            } else {
                Log.d(MainActivity.class.getName(), "get failed with ", task.getException());
            }
        });
    }

    @Override
    public void manageVisibilityForGuestUsrFeature(String email, MenuItem registerMenu, Menu menu, Button btn){
        fbConnector.firebaseSetup();
        FirebaseFirestore db = fbConnector.getDb();
        DocumentReference docRef = db.collection(FS_USERS_COLLECTION).document(email);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                assert document != null;
                if (document.exists()) {
                    User generalUser = document.toObject(GeneralUser.class);
                    if(generalUser.getRole() != UserConstant.GENERAL_USER_ROLE){
                        Log.i(TAG, email + " : Not a general user.");
                        // disable register option in menu for general user
                        if(registerMenu != null)
                            registerMenu.setVisible(false);
                    }
                    else {
                        // disable ask a question & messaging for general user
                        if(menu != null){
                            menu.getItem(0).setVisible(false);
                            menu.getItem(1).setVisible(false);
                        }
                        if(btn != null){
                            btn.setVisibility(View.VISIBLE);
                        }
                    }
                } else {
                    Log.d(MainActivity.class.getName(), "No such document with email " + email);
                }
            } else {
                Log.d(MainActivity.class.getName(), "get failed with ", task.getException());
            }
        });
    }
}
