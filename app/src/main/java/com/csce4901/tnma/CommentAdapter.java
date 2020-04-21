package com.csce4901.tnma;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.csce4901.tnma.DAO.BlogDao;
import com.csce4901.tnma.DAO.Impl.BlogDaoImpl;
import com.csce4901.tnma.Models.Blog;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentHolder> {

    /*
        numComment = commentCount
        data1 = commentText
        data2 = commenterEmail
        data3 = commentDate
     */

    String[] data1, data2, data3;
    Context context;

    public CommentAdapter(Context ct, String[]s1, String[] s2, String[] s3)
    {
        context = ct;
        data1 = s1;
        data2 = s2;
        data3 = s3;

    }

    @NonNull
    @Override
    public CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.comments_row, parent, false);
        return new CommentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentHolder holder, int position) {
        holder.cText.setText(data1[position]);
        holder.cEmail.setText(data2[position]);
        holder.cDate.setText(data3[position].substring(0,10));
    }

    @Override
    public int getItemCount() {
        return data1.length;
    }

    public class CommentHolder extends RecyclerView.ViewHolder {

        TextView cText, cEmail, cDate;

        public CommentHolder(@NonNull View itemView) {
            super(itemView);
            //comments_row.xml
            cText = itemView.findViewById(R.id.commentText);
            cEmail = itemView.findViewById(R.id.commenterID);
            cDate = itemView.findViewById(R.id.commentDate);
        }
    }
}
