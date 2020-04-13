package com.csce4901.tnma;

import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.csce4901.tnma.DAO.GeneralUserDao;
import com.csce4901.tnma.DAO.Impl.GeneralUserDaoImpl;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView etitle,edesc;
    private ImageView eimage;

    public Home() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
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
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        Button memberButton = (Button) v.findViewById(R.id.memberBtn);
        etitle = (TextView) v.findViewById(R.id.item_title);
        edesc = (TextView) v.findViewById(R.id.item_description);
        eimage = (ImageView) v.findViewById(R.id.image_1);


        memberButton.setVisibility(View.INVISIBLE);
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            GeneralUserDao user = new GeneralUserDaoImpl();
            user.manageVisibilityForGuestUsrFeature(FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                    null, null, memberButton);
        }
        memberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Questionnaire.class);
                startActivity(intent);
            }
        });

        //to do abiral
        //set etitle and edesc from feaatured event in database..the eimage can be left for now
        eimage.setImageResource(R.drawable.eat);
        etitle.setText("featured title");
        edesc.setText("featured description");


        return v;
    }
}
