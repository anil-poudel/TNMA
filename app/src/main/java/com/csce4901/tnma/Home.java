package com.csce4901.tnma;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.csce4901.tnma.DAO.BlogDao;
import com.csce4901.tnma.DAO.EventDao;
import com.csce4901.tnma.DAO.GeneralUserDao;
import com.csce4901.tnma.DAO.Impl.BlogDaoImpl;
import com.csce4901.tnma.DAO.Impl.EventDaoImpl;
import com.csce4901.tnma.DAO.Impl.GeneralUserDaoImpl;
import com.google.firebase.auth.FirebaseAuth;

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

    private Unbinder unbinder;
    @BindView(R.id.item_title2) TextView fBlogTitle;
    @BindView(R.id.item_description2) TextView fBlogDesc;
    @BindView(R.id.image_2) ImageView fBlogImg;
    @BindView(R.id.item_title) TextView fEventitle;
    @BindView(R.id.item_description) TextView fEventDesc;
    @BindView(R.id.image_1) ImageView fEventImg;
    @BindView(R.id.memberBtn) Button memberButton;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, v);
        memberButton.setVisibility(View.INVISIBLE);
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            GeneralUserDao user = new GeneralUserDaoImpl();
            user.manageVisibilityForGuestUsrFeature(FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                    null, memberButton, null);
        }
        memberButton.setOnClickListener(v1 -> {
            Intent intent = new Intent(getContext(), Questionnaire.class);
            startActivity(intent);
        });

        EventDao eventDao = new EventDaoImpl();
        BlogDao blogDao = new BlogDaoImpl();
        blogDao.getFeaturedBlog(fBlogTitle, fBlogDesc, fBlogImg);
        eventDao.getFeaturedEvents(fEventitle, fEventDesc, fEventImg);
        return v;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
