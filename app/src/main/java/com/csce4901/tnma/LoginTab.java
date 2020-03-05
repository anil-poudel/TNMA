package com.csce4901.tnma;

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


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginTab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginTab extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Unbinder unbinder;

    @BindView(R.id.email) EditText email;
    @BindView(R.id.passcode) EditText password;

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
        return v;
    }

    @OnClick(R.id.loginButton)
    public void submit() {
        if(email.getText().toString().equals("admin@gmail.com")) {
            Toast.makeText(MainActivity.getAppContext(), "Logged in as ADMIN!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(MainActivity.getAppContext(), "Invalid credentials!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
