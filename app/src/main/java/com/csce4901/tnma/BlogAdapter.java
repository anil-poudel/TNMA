package com.csce4901.tnma;

import android.content.Context;
import android.content.Intent;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.csce4901.tnma.DAO.BlogDao;
import com.csce4901.tnma.DAO.Impl.BlogDaoImpl;
import com.csce4901.tnma.Models.Blog;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.BlogHolder> {

    /*
        data1 = title
        data2 = author
        data3 = date
        data4 = description
        data5 = imageURL
        data6 = boostCount
        data7 = commentCount
     */

    String[] data1;
    String[] data2;
    String[] data3;
    String[] data4;
    String[] data5;
    Integer[] data6;
    Integer[] data7;
    Context context;

    public BlogAdapter(Context ct, String[] s1, String[] s2,String[] s3,String[] s4,String[] s5, Integer[] s6, Integer[] s7){
        context = ct;
        data1 = s1;
        data2 = s2;
        data3 = s3;
        data4 = s4;
        data5 = s5;
        data6 = s6;
        data7 = s7;
    }


    @NonNull
    @Override
    public BlogHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.blogs_row,parent,false);
        return new BlogHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BlogHolder holder, int position) {
        holder.ptitle.setText(data1[position]);
        holder.pauthor.setText(data2[position]);
        holder.pdate.setText(data3[position]);
        holder.pdesc.setText(data4[position]);
        holder.boostBtn.setText(String.valueOf(data6[position]));
        holder.boostedBtn.setText(String.valueOf(data6[position]));
        holder.commentBtn.setText(String.valueOf(data7[position]));

        BlogDao blogDao = new BlogDaoImpl();
        //Load Blogpost Image from database
        Picasso.get()
                .load(data5[position])
                .resize(1024,768)
                .centerCrop(Gravity.START)
                .into(holder.pimage);

        //Expand view when clicked on dropdown or post itself
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.expandedView.getVisibility()==View.GONE)
                {
                    TransitionManager.beginDelayedTransition(holder.mainLayout, new AutoTransition());
                    holder.expandedView.setVisibility(View.VISIBLE);
                    holder.moreLess.setImageResource(R.drawable.ic_less);
                }
                else
                {
                    TransitionManager.beginDelayedTransition(holder.mainLayout, new AutoTransition());
                    holder.expandedView.setVisibility(View.GONE);
                    holder.moreLess.setImageResource(R.drawable.ic_more);
                }
            }
        });

        //Boost Button
        holder.boostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.boostedBtn.getVisibility() == View.GONE)
                {
                    blogDao.boostCount(data1[position], data6[position]+1);
                    holder.boostBtn.setVisibility(View.GONE);
                    holder.boostedBtn.setVisibility(View.VISIBLE);
                }
            }
        });
        //Boosted Button
        holder.boostedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.boostBtn.getVisibility() == View.GONE)
                {
                    blogDao.boostCount(data1[position], data6[position]-1);
                    holder.boostedBtn.setVisibility(View.GONE);
                    holder.boostBtn.setVisibility(View.VISIBLE);
                }
            }
        });

        //Comment Button
        holder.commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent comments = new Intent(context, Comment.class);
                comments.putExtra("title", data1[position]);
                context.startActivity(comments);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data1.length;
    }

    public class BlogHolder extends RecyclerView.ViewHolder{

        TextView ptitle, pauthor, pdesc, pdate;
        ImageView pimage, moreLess;
        CardView mainLayout;
        Button boostBtn, boostedBtn, commentBtn;
        LinearLayout expandedView;

        public BlogHolder(@NonNull View itemView) {
            super(itemView);
            //Views from blogs_row.xml
            mainLayout = itemView.findViewById(R.id.cardLayout);
            expandedView = itemView.findViewById(R.id.postExpandedView);
            moreLess = itemView.findViewById(R.id.moreLess);
            ptitle = itemView.findViewById(R.id.postTitle);
            pdesc = itemView.findViewById(R.id.postDescription);
            pauthor = itemView.findViewById(R.id.postAuthor);
            pdate = itemView.findViewById(R.id.postDate);
            pimage = itemView.findViewById(R.id.postImage);
            boostBtn = itemView.findViewById(R.id.boostButton);
            boostedBtn = itemView.findViewById(R.id.boostedButton);
            commentBtn = itemView.findViewById(R.id.commentButton);
        }
    }

}
