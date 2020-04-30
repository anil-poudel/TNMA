package com.csce4901.tnma.chat_adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.csce4901.tnma.Connector.FirebaseConnector;
import com.csce4901.tnma.Models.Chat;
import com.csce4901.tnma.R;
import com.csce4901.tnma.chatting;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class chattingAdapter extends RecyclerView.Adapter<chattingAdapter.ViewHolder> {

    public static  final int MSG_TYPE_LEFT = 0;
    public static  final int MSG_TYPE_RIGHT = 1;

    private final String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();


    private List<Chat> mChats;

    private Context mContext;


    public chattingAdapter(Context mContext, List<Chat> mChats)
    {
        this.mChats = mChats;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public chattingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_right, parent, false);
            return new chattingAdapter.ViewHolder(view);
        }else
         {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_left, parent, false);
            return new chattingAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull chattingAdapter.ViewHolder holder, int position) {
        Chat chat = mChats.get(position);
        holder.show_message.setText(chat.getMessage());
    }

    @Override
    public int getItemCount() {
        return mChats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView show_message;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            show_message = itemView.findViewById(R.id.show_message);


        }
    }

    public int getItemViewType(int position){

        if(mChats.get(position).getSender().equals(email)){
            return MSG_TYPE_RIGHT;
        }else
        {
            return MSG_TYPE_LEFT;
        }

    }
}
