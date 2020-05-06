package com.csce4901.tnma;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.csce4901.tnma.DAO.Impl.QuestionDaoImpl;
import com.csce4901.tnma.DAO.QuestionDao;
import com.csce4901.tnma.Models.GeneralUser;
import com.google.firebase.auth.FirebaseAuth;

public class QNA_Adapter extends RecyclerView.Adapter<QNA_Adapter.QNAHolder> {

    String[] data1; //question
    String[] data2; //answerlist
    String[] data3; //answeredbylist
    Context context;

    public QNA_Adapter(Context ct, String[] s1, String[] s2, String[] s3)
    {
        data1 = s1;
        data2 = s2;
        data3 = s3;
        context = ct;
    }

    @NonNull
    @Override
    public QNA_Adapter.QNAHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.qna_parent_row, parent, false);
        return new QNAHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QNAHolder holder, int position) {
        //question text
        holder.questionText.setText(data1[position]);
        //answer Text
        holder.answerText.setText(data2[position]);
        holder.answeredByText.setText(data3[position]);
        //Call answer implementation
        QuestionDao questionDao = new QuestionDaoImpl();

        String userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        //TODO:Abiral, Set qnaAnswerButton visibility based on user role i.e. Visible only for mentor
        questionDao.manageVisibility(userEmail, holder.answerButton);

        //Answer button for mentors
        holder.answerButton.setOnClickListener(v->{
            holder.builder.setView(holder.inputAnswer);

            holder.builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String answerText = holder.inputAnswer.getText().toString();
                    questionDao.setMentorAnswer(data1[position], answerText);
                    Toast.makeText(context,"Answer posted.", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
            holder.builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            holder.builder.show();
        });

    }

    @Override
    public int getItemCount() {
        return data1.length;
    }


    public class QNAHolder extends RecyclerView.ViewHolder{

        TextView questionText, answerText, answeredByText;
        Button answerButton;
        AlertDialog.Builder builder;
        EditText inputAnswer;

        public QNAHolder(@NonNull View itemView) {
            super(itemView);
            builder = new AlertDialog.Builder(context);
            builder.setTitle("Please enter your answer.");
            inputAnswer = new EditText(context);
            inputAnswer.setInputType(InputType.TYPE_CLASS_TEXT);

            questionText = itemView.findViewById(R.id.qnaQuestion);
            answerText = itemView.findViewById(R.id.qnaAnswer);
            answeredByText = itemView.findViewById(R.id.qnaAnsweredBy);
            answerButton = itemView.findViewById(R.id.qnaAnswerBtn);
        }
    }
}
