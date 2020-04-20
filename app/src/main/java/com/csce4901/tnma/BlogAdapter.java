package com.csce4901.tnma;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.csce4901.tnma.DAO.BlogDao;
import com.csce4901.tnma.DAO.Impl.BlogDaoImpl;
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
        data8 = hasBoosted?
     */

    final String userEmail = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();
    String[] data1;
    String[] data2;
    String[] data3;
    String[] data4;
    String[] data5;
    Integer[] data6;
    Integer[] data7;
    Boolean[] data8;
    Context context;

    public BlogAdapter(Context ct, String[] s1, String[] s2,String[] s3,String[] s4,String[] s5, Integer[] s6, Integer[] s7, Boolean[] s8){
        context = ct;
        data1 = s1;
        data2 = s2;
        data3 = s3;
        data4 = s4;
        data5 = s5;
        data6 = s6;
        data7 = s7;
        data8 = s8;
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
        holder.pdate.setText(data3[position].substring(0, 10));
        holder.pdesc.setText(data4[position]);
        holder.boostBtn.setText(String.valueOf(data6[position]));
        holder.commentBtn.setText(String.valueOf(data7[position]));

        BlogDao blogDao = new BlogDaoImpl();
       //Load Blogpost Image from database
        Picasso.get()
                .load(data5[position])
                .into(holder.pimage);

        //Expand view when clicked on dropdown or post itself
        holder.mainLayout.setOnClickListener(v -> {
            if (holder.expandedView.getVisibility() == View.GONE) {
                TransitionManager.beginDelayedTransition(holder.mainLayout, new AutoTransition());
                holder.expandedView.setVisibility(View.VISIBLE);
                holder.moreLess.setImageResource(R.drawable.ic_less);
            } else {
                TransitionManager.beginDelayedTransition(holder.mainLayout, new AutoTransition());
                holder.expandedView.setVisibility(View.GONE);
                holder.moreLess.setImageResource(R.drawable.ic_more);
            }
        });


        //Initial Setup for boost Button
        if (data8[position] == null) {
            //if has no data about hasBoosted
            //set original image button
            holder.boostBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_boost1, 0, 0, 0);
        } else {
            //if hasBoosted==true
            //set boosted image button
            if (data8[position]) {
                holder.boostBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_boost2, 0, 0, 0);
            } else {
                holder.boostBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_boost1, 0, 0, 0);
            }
        }

        //When clicked check if already boosted,
        //if not boosted, boost the post
        //and vice versa
        //update graphics as needed
        holder.boostBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                //if data doesnot exist
                //create new data
                if (data8[position] == null) {
                    blogDao.boostCount(data1[position], data6[position] + 1, userEmail, true);
                    holder.boostBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_boost2, 0, 0, 0);
                    holder.boostBtn.setTextColor(R.color.Accent);
                } else {
                    //if data exists and is true
                    //unboost
                    if (data8[position]) {
                        blogDao.boostCount(data1[position], data6[position] - 1, userEmail, false);
                        holder.boostBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_boost1, 0, 0, 0);
                        holder.boostBtn.setTextColor(R.color.Black);
                    } else {
                        //if data exists and is false
                        // boost
                        blogDao.boostCount(data1[position], data6[position] + 1, userEmail, true);
                        holder.boostBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_boost2, 0, 0, 0);
                        holder.boostBtn.setTextColor(R.color.Accent);
                    }
                }
            }
        });

        //Comment Button
        holder.commentBtn.setOnClickListener(v -> {
                Intent comments = new Intent(context, Comment.class);
                comments.putExtra("title", data1[position]);
                context.startActivity(comments);
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
            Button boostBtn, commentBtn;
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
                commentBtn = itemView.findViewById(R.id.commentButton);
            }
        }
    }
