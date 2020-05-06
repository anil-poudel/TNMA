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
import android.widget.ImageView;

import com.csce4901.tnma.Connector.FirebaseConnector;
import com.csce4901.tnma.DAO.BlogDao;
import com.csce4901.tnma.DAO.Impl.BlogDaoImpl;
import com.csce4901.tnma.Models.GeneralUser;
import com.csce4901.tnma.Models.Mentor;
import com.csce4901.tnma.Models.Student;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.csce4901.tnma.Constants.UserConstant.FS_USERS_COLLECTION;
import static com.csce4901.tnma.Constants.UserConstant.GENERAL_USER_ROLE;
import static com.csce4901.tnma.Constants.UserConstant.MENTOR_ROLE;
import static com.csce4901.tnma.Constants.UserConstant.STUDENT_ROLE;


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
    private int userRole;
    ImageView img;

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

        FirebaseConnector fbconnector = new FirebaseConnector();
        fbconnector.firebaseSetup();

        String userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        //Setup Visibility
        FirebaseFirestore db = fbconnector.getDb();
        db.collection(FS_USERS_COLLECTION)
                .document(userEmail)
                .get()
                .addOnCompleteListener(task -> {
                    GeneralUser generalUser = task.getResult().toObject(GeneralUser.class);
                    userRole = generalUser.getRole();

                    if(userRole == GENERAL_USER_ROLE)
                    {
                        createPostButton.setVisibility(View.GONE);
                    }

                    if(userRole == STUDENT_ROLE)
                    {
                        Student student = task.getResult().toObject(Student.class);
                        if(!student.isRoleVerified())
                        {
                            createPostButton.setVisibility(View.GONE);
                        }
                    }
                    if(userRole == MENTOR_ROLE)
                    {
                        Mentor mentor = task.getResult().toObject(Mentor.class);
                        if(!mentor.isRoleVerified())
                        {
                            createPostButton.setVisibility(View.GONE);
                        }
                    }
                });

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
