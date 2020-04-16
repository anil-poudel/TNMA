package com.csce4901.tnma;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


public class Blogs extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private Button createPostButton;

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
        createPostButton.setOnClickListener(this::onClick);

        return view;
    }

    private void onClick(View v) {
        Intent intent = new Intent(getContext(), CreatePost.class);
        startActivity(intent);
    }
}
