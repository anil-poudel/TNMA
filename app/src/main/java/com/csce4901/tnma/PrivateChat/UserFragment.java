package com.csce4901.tnma.PrivateChat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csce4901.tnma.Connector.FirebaseConnector;
import com.csce4901.tnma.Models.GeneralUser;
import com.csce4901.tnma.Models.Mentor;
import com.csce4901.tnma.Models.Student;
import com.csce4901.tnma.Models.User;
import com.csce4901.tnma.R;
import com.csce4901.tnma.chat_adapter.UserAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.csce4901.tnma.Constants.UserConstant.FS_USERS_COLLECTION;
import static com.csce4901.tnma.Constants.UserConstant.STUDENT_ROLE;

public class UserFragment extends Fragment {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<String> mUsers;

    private final String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mUsers = new ArrayList<>();

        readUsers();

        return view;
    }

    private void readUsers() {

        FirebaseConnector fbConnector = new FirebaseConnector();
        fbConnector.firebaseSetup();
        FirebaseFirestore db = fbConnector.getDb();

        DocumentReference docRef = db.collection(FS_USERS_COLLECTION).document(email);

        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot documentSnapshot = task.getResult();
                assert documentSnapshot != null;

                if (documentSnapshot.exists()) {
                    User currentUser = documentSnapshot.toObject(GeneralUser.class);
                    if (currentUser.getRole() == STUDENT_ROLE) {
                        Student student = documentSnapshot.toObject(Student.class);
                        mUsers.add(student.getMentor());
                    } else {
                        Mentor mentor = documentSnapshot.toObject(Mentor.class);
                        if(mentor.getStudents() != null){
                            for (int i = 0; i < mentor.getStudents().size(); i++) {
                                mUsers.add(mentor.getStudents().get(i));
                            }
                        }
                    }

                    userAdapter = new UserAdapter(getContext(), mUsers);
                    recyclerView.setAdapter(userAdapter);
                }
                else
                {
                    Log.d(TAG, "No users found", task.getException());
                }
            }
        });
    }
}


