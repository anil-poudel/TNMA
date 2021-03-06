package com.csce4901.tnma;

import android.app.Dialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.csce4901.tnma.Connector.FirebaseConnector;
import com.google.firebase.auth.FirebaseAuth;

import static com.csce4901.tnma.Validator.InputValidator.EMAIL;
import static com.csce4901.tnma.Validator.InputValidator.PASSWORD;

public class LoginTab extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Unbinder unbinder;
    private FirebaseAuth firebaseAuth;
    FirebaseConnector fbConnector = new FirebaseConnector();

    //for Guest Login bypass
    boolean bypass = false;

    @BindView(R.id.email) EditText email;
    @BindView(R.id.passcode) EditText password;
    @BindView(R.id.forgot) TextView rForgotPassword;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginTab() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginTab.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginTab newInstance(String param1, String param2) {
        LoginTab fragment = new LoginTab();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login_tab, container, false);
        unbinder = ButterKnife.bind(this, v);
        rForgotPassword.setOnClickListener(forgotBtn -> {
            Dialog resetDialog = null;
            resetDialog = new Dialog(forgotBtn.getContext());
            resetDialog.setContentView(R.layout.reset_password_popup);
            Button rCancelBtn = resetDialog.findViewById(R.id.resetPass_CancelBtn);
            Button resetBtn = resetDialog.findViewById(R.id.resetPass_Btn);
            resetDialog.show();
            Dialog finalResetDialog = resetDialog;
            rCancelBtn.setOnClickListener(cancel -> finalResetDialog.dismiss());
            resetBtn.setOnClickListener(reset -> {
                EditText resetEmail = finalResetDialog.findViewById(R.id.reset_email);
                String emailAddr = resetEmail.getText().toString();
                firebaseAuth = fbConnector.getFirebaseAuthInstance();
                if(isNullOrEmpty(emailAddr)){
                    Toast.makeText(getContext(), "Empty field, try again",
                            Toast.LENGTH_LONG).show();
                } else {
                    firebaseAuth.sendPasswordResetEmail(emailAddr).addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.getAppContext(),
                                    "Reset email sent",
                                    Toast.LENGTH_LONG).show();
                            finalResetDialog.dismiss();
                        }
                    });
                }
            });
        });
        return v;
    }

    @OnClick(R.id.loginButton)
    public void submit() {
        firebaseAuth = fbConnector.getFirebaseAuthInstance();
        String user = email.getText().toString();
        String pass = password.getText().toString();

        //TODO: Error Checking for Email and Password Syntax
        if(!EMAIL.validateInput(EMAIL.name(), user))
        {
            Toast.makeText(getContext(), "Invalid Email", Toast.LENGTH_LONG).show();
        }
        else if (!PASSWORD.validateInput(PASSWORD.name(), pass)) {
            Toast.makeText(getContext(), "Invalid Password", Toast.LENGTH_LONG).show();
        } else {
            firebaseAuth.signInWithEmailAndPassword(user, pass)
                    .addOnCompleteListener(
                            task -> {
                                if (task.isSuccessful()) {
                                    // if sign-in is successful
                                    if(firebaseAuth.getCurrentUser().isEmailVerified()){
                                        Toast.makeText(MainActivity.getAppContext(),
                                                "Login successful!!",
                                                Toast.LENGTH_LONG)
                                                .show();
                                        //If email verified, first time login
                                        //TODO: Goto Questionnaire intent

                                        //If not first login, goto dashboard
                                        Intent intent
                                                = new Intent(MainActivity.getAppContext(),
                                                Dashboard.class);
                                        startActivity(intent);
                                    } else {
                                        //If email not verified, ask to verify
                                        Toast.makeText(MainActivity.getAppContext(),
                                                "Please verify your email address",
                                                Toast.LENGTH_LONG)
                                                .show();
                                    }
                                } else {
                                    // sign-in failed
                                    Toast.makeText(MainActivity.getAppContext(),
                                            "Unable to sign in.\nPlease check your email and/or password.",
                                            Toast.LENGTH_LONG)
                                            .show();
                                }
                            });
        }
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public static boolean isNullOrEmpty(String str) {
        if(str != null && !str.isEmpty())
            return false;
        return true;
    }

    public interface OnFragmentInteractionListener {
    }
}
