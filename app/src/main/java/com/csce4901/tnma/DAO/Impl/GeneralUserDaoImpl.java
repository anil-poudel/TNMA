package com.csce4901.tnma.DAO.Impl;

import android.graphics.drawable.Drawable;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.csce4901.tnma.Connector.FirebaseConnector;
import com.csce4901.tnma.DAO.GeneralUserDao;
import com.csce4901.tnma.MainActivity;
import com.csce4901.tnma.Models.GeneralUser;
import com.csce4901.tnma.Models.Mentor;
import com.csce4901.tnma.Models.Student;
import com.csce4901.tnma.Models.User;
import com.csce4901.tnma.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.csce4901.tnma.Constants.UserConstant.ADMIN_ROLE;
import static com.csce4901.tnma.Constants.UserConstant.FS_USERS_COLLECTION;
import static com.csce4901.tnma.Constants.UserConstant.GENERAL_USER_ROLE;
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
    public void getUserAvatar(String email, TextView avatar, Context ctx){
        fbConnector.firebaseSetup();
        FirebaseFirestore db = fbConnector.getDb();
        DocumentReference docRef = db.collection(FS_USERS_COLLECTION).document(email);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                assert document != null;
                if (document.exists()) {
                    User generalUser = document.toObject(GeneralUser.class);
                    int selectedAvatar = generalUser.getAvatar();
                    Drawable[] compoundDrawables;
                    compoundDrawables = avatar.getCompoundDrawables();
                    //Sid update view based on the integer
                    switch (selectedAvatar) {
                        case 0: {
                            Drawable img0 = ctx.getResources().getDrawable(R.drawable.tnma);
                            avatar.setCompoundDrawablesRelativeWithIntrinsicBounds(compoundDrawables[0], img0, compoundDrawables[2], compoundDrawables[3]);
                            break;
                        }
                        case 1: {
                            Drawable img1 = ctx.getResources().getDrawable(R.drawable.profile);
                            avatar.setCompoundDrawablesRelativeWithIntrinsicBounds(compoundDrawables[0], img1, compoundDrawables[2], compoundDrawables[3]);
                            break;
                        }
                        case 2: {
                            Drawable img2 = ctx.getResources().getDrawable(R.drawable.scientist);
                            avatar.setCompoundDrawablesRelativeWithIntrinsicBounds(compoundDrawables[0], img2, compoundDrawables[2], compoundDrawables[3]);
                            break;
                        }
                        case 3: {
                            Drawable img3 = ctx.getResources().getDrawable(R.drawable.goldmedal);
                            avatar.setCompoundDrawablesRelativeWithIntrinsicBounds(compoundDrawables[0], img3, compoundDrawables[2], compoundDrawables[3]);
                            break;
                        }
                    }
                } else {
                    Log.e(TAG, "User does not exist: " + email);
                }
            } else {
                Log.e(TAG, "get failed with ", task.getException());
            }
        });
    }

    @Override
    public void setRoleAvatar(String email, TextView role, Context ctx){
        fbConnector.firebaseSetup();
        FirebaseFirestore db = fbConnector.getDb();
        DocumentReference docRef = db.collection(FS_USERS_COLLECTION).document(email);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                assert document != null;
                if (document.exists()) {
                    Drawable[] compoundDrawables;
                    compoundDrawables = role.getCompoundDrawables();
                    User generalUser = document.toObject(GeneralUser.class);
                    if(generalUser.getRole() == STUDENT_ROLE){
                        Drawable img0 = ctx.getResources().getDrawable(R.drawable.student);
                        role.setCompoundDrawablesRelativeWithIntrinsicBounds(compoundDrawables[0], img0, compoundDrawables[2], compoundDrawables[3]);
                    }
                    if(generalUser.getRole() == MENTOR_ROLE){
                        Drawable img1 = ctx.getResources().getDrawable(R.drawable.doctor);
                        role.setCompoundDrawablesRelativeWithIntrinsicBounds(compoundDrawables[0], img1, compoundDrawables[2], compoundDrawables[3]);
                    }
                } else {
                    Log.e(TAG, "User does not exist: " + email);
                }
            } else {
                Log.e(TAG, "get failed with ", task.getException());
            }
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

    //For profile pop-up information card. Not an ideal approach, but I could not get this to work for string parameters.
    public void getUserProfileEditInfo(String email, TextView editfName, TextView editlName,
                                       TextView editName, TextView editPhone, TextView editCity, TextView editState){
        fbConnector.firebaseSetup();
        FirebaseFirestore db = fbConnector.getDb();
        DocumentReference docRef = db.collection(FS_USERS_COLLECTION).document(email);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                assert document != null;
                if (document.exists()) {
                    StringBuilder fName = null;
                    StringBuilder lName = null;
                    StringBuilder name = null;
                    String phone = null;
                    String city = null;
                    String state = null;
                    User generalUser = document.toObject(GeneralUser.class);
                    if(generalUser.getRole() == STUDENT_ROLE){
                        Student student = document.toObject(Student.class);
                        fName = new StringBuilder()
                                .append(student.getFname());
                        lName = new StringBuilder()
                                .append(student.getLname());
                        name = new StringBuilder()
                                .append(fName)
                                .append(" ")
                                .append(lName);
                        phone = student.getPhone();
                        city = student.getCity();
                        state = student.getState();
                    }
                    if(generalUser.getRole() == MENTOR_ROLE){
                        Mentor mentor = document.toObject(Mentor.class);
                        fName = new StringBuilder()
                                .append(mentor.getFname());
                        lName = new StringBuilder()
                                .append(mentor.getLname());
                        name = new StringBuilder()
                                .append(fName)
                                .append(" ")
                                .append(lName);
                        phone = mentor.getPhone();
                        city = mentor.getCity();
                        state = mentor.getState();
                    }
                    editfName.setText(fName);
                    editlName.setText(lName);
                    editName.setText(name);
                    editPhone.setText(phone);
                    editCity.setText(city);
                    editState.setText(state);
                } else {
                    Log.d(MainActivity.class.getName(), "No such document with email " + email);
                }
            } else {
                Log.d(MainActivity.class.getName(), "get failed with ", task.getException());
            }
        });
    }


    @Override
    public void manageVisibilityForGuestUsrFeature(String email, Menu menu, Button btn, MenuItem profileMenuItem){
        fbConnector.firebaseSetup();
        FirebaseFirestore db = fbConnector.getDb();
        DocumentReference docRef = db.collection(FS_USERS_COLLECTION).document(email);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                assert document != null;
                if (document.exists()) {
                    User generalUser = document.toObject(GeneralUser.class);
                    int currUserRole = generalUser.getRole();
                    if(currUserRole != GENERAL_USER_ROLE){
                        Log.i(TAG, email + " : Not a general user.");
                        if(menu != null && !generalUser.isRoleVerified()){
                            menu.getItem(1).setVisible(false);
                        }
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
                        if(profileMenuItem != null) {
                            if (currUserRole == GENERAL_USER_ROLE || currUserRole == ADMIN_ROLE) {
                                profileMenuItem.setVisible(false);
                            }
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

    @Override
    public void updateUserProfileInfo(String email, String fname, String lname, String phone, String city, String state) {
        fbConnector.firebaseSetup();
        FirebaseFirestore db = fbConnector.getDb();
        DocumentReference docRef = db.collection(FS_USERS_COLLECTION).document(email);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                assert document != null;
                if (document.exists()) {
                    User generalUser = document.toObject(GeneralUser.class);
                    //update user info
                    if(generalUser.getRole() == STUDENT_ROLE){
                        Student student = document.toObject(Student.class);
                        student.setFname(fname);
                        student.setLname(lname);
                        student.setPhone(phone);
                        student.setCity(city);
                        student.setState(state);
                        db.collection(FS_USERS_COLLECTION)
                                .document(email)
                                .set(student);
                    }
                    if(generalUser.getRole() == MENTOR_ROLE) {
                        Mentor mentor = document.toObject(Mentor.class);
                        mentor.setFname(fname);
                        mentor.setLname(lname);
                        mentor.setPhone(phone);
                        mentor.setCity(city);
                        mentor.setState(state);
                        db.collection(FS_USERS_COLLECTION)
                                .document(email)
                                .set(mentor);
                    }
                } else {
                    Log.e(TAG, "User does not exist: " + email);
                }
            } else {
                Log.e(TAG, "get failed with ", task.getException());
            }
        });
    }

    @Override
    public void setUserAvatar(String email, int avatarNum) {
        fbConnector.firebaseSetup();
        FirebaseFirestore db = fbConnector.getDb();
        DocumentReference docRef = db.collection(FS_USERS_COLLECTION).document(email);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                assert document != null;
                if (document.exists()) {
                    User generalUser = document.toObject(GeneralUser.class);
                    //update user info
                    if(generalUser.getRole() == STUDENT_ROLE){
                        Student student = document.toObject(Student.class);
                        student.setAvatar(avatarNum);
                        db.collection(FS_USERS_COLLECTION)
                                .document(email)
                                .set(student);
                    }
                    if(generalUser.getRole() == MENTOR_ROLE) {
                        Mentor mentor = document.toObject(Mentor.class);
                        mentor.setAvatar(avatarNum);
                        db.collection(FS_USERS_COLLECTION)
                                .document(email)
                                .set(mentor);
                    }
                } else {
                    Log.e(TAG, "User does not exist: " + email);
                }
            } else {
                Log.e(TAG, "get failed with ", task.getException());
            }
        });
    }
}
