package com.csce4901.tnma;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.csce4901.tnma.Connector.FirebaseConnector;
import com.csce4901.tnma.DAO.GeneralUserDao;
import com.csce4901.tnma.DAO.Impl.GeneralUserDaoImpl;
import com.google.firebase.auth.FirebaseAuth;

import static com.csce4901.tnma.Validator.InputValidator.EMAIL;
import static com.csce4901.tnma.Validator.InputValidator.PASSWORD;

public class SignupTab extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Unbinder unbinder;
    private FirebaseAuth firebaseAuth;
    FirebaseConnector fbConnector = new FirebaseConnector();

    @BindView(R.id.emailSignup) EditText userEmail;
    @BindView(R.id.passcode1) EditText userPass;
    @BindView(R.id.passcode2) EditText userPassConfirm;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignupTab() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SignupTab newInstance(String param1, String param2) {
        SignupTab fragment = new SignupTab();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @OnClick(R.id.signupButton)
    public void signup(){
        Context context = MainActivity.getAppContext();
        firebaseAuth = fbConnector.getFirebaseAuthInstance();
        GeneralUserDao generalUserDao = new GeneralUserDaoImpl();
        String user = userEmail.getText().toString();
        String pass = userPass.getText().toString();
        String passConfirm = userPassConfirm.getText().toString();

        //TODO: Error Checking for Email and Password Syntax
        if(!EMAIL.validateInput(EMAIL.name(), user))
        {
            Toast.makeText(getContext(), "Invalid Email", Toast.LENGTH_LONG).show();
        }
        else if (!PASSWORD.validateInput(PASSWORD.name(), pass)) {
            Toast.makeText(getContext(), "Invalid Password", Toast.LENGTH_LONG).show();
        } else {
            if(pass.equals(passConfirm)) {

                firebaseAuth
                        .createUserWithEmailAndPassword(user, pass)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                generalUserDao.createGeneralUser(user);
                                firebaseAuth.getCurrentUser().sendEmailVerification()
                                        .addOnCompleteListener(task1 -> {
                                            if(task1.isSuccessful()){
                                                Toast.makeText(context,
                                                        "Registration successful. Please check email for verification.",
                                                        Toast.LENGTH_LONG)
                                                        .show();
                                            } else {
                                                Toast.makeText(context,
                                                        task1.getException().getMessage(),
                                                        Toast.LENGTH_LONG)
                                                        .show();
                                            }
                                        });
                                // if the user created intent to login activity
                                Intent intent
                                        = new Intent(context,
                                        MainActivity.class);
                                startActivity(intent);
                            } else {
                                // Registration failed
                                Toast.makeText(
                                        context,
                                        "Registration failed!!"
                                                + " Please try again later",
                                        Toast.LENGTH_LONG)
                                        .show();
                            }
                        });
            } else {
                Toast.makeText(getContext(), "Passwords do not match.", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_signup_tab, container, false);
        unbinder = ButterKnife.bind(this, v);
        return v;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public interface OnFragmentInteractionListener {
    }
}
