package com.csce4901.tnma;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.csce4901.tnma.DAO.BlogDao;
import com.csce4901.tnma.DAO.Impl.BlogDaoImpl;
import com.google.firebase.auth.FirebaseAuth;


public class Blogs extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button createPostButton;
    RecyclerView postRecyclerView;

    public Blogs() {
        // Required empty public constructor
    }

    public static Blogs newInstance(String param1, String param2) {
        Blogs fragment = new Blogs();
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
        View view = inflater.inflate(R.layout.fragment_blogs, container, false);

        createPostButton = view.findViewById(R.id.createBlogButton);
        postRecyclerView = view.findViewById(R.id.postsRecyclerView);

        String userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        createPostButton.setOnClickListener(this::onClick);

        //Recyclerview data
        FragmentActivity fragmentActivity = getActivity();
        BlogDao blogDao = new BlogDaoImpl();
        blogDao.getAllBlogs(userEmail, postRecyclerView, fragmentActivity);
        return view;
    }

    private void onClick(View v) {
        Intent intent = new Intent(getContext(), CreatePost.class);
        startActivity(intent);
    }
}
