package com.csce4901.tnma;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventHolder> {

    String data1[], data2[],data3[],data4[],data5[];
    int images[];
    Context context;

    public EventAdapter(Context ct, String[] s1, String[] s2,String[] s3,String[] s4,String[] s5, int[] img){
        context = ct;
        data1 = s1;
        data2 = s2;
        data3 = s3;
        data4 = s4;
        data5 = s5;
        images = img;
    }


    @NonNull
    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_event_viewer,parent,false);

        return new EventHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventHolder holder, int position) {
        holder.etile.setText(data1[position]);
        holder.edesc.setText(data2[position]);
        holder.eaddress.setText(data3[position]);
        holder.etime.setText(data4[position]);
        holder.edate.setText(data5[position]);
        holder.eimage.setImageResource(images[position]);

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,EventPopUp.class);
                intent.putExtra("data1",data1[position]);
                intent.putExtra("data2",data2[position]);
                intent.putExtra("data3",data3[position]);
                intent.putExtra("data4",data4[position]);
                intent.putExtra("data5",data5[position]);
                intent.putExtra("images",images[position]);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public class EventHolder extends RecyclerView.ViewHolder{

        TextView etile,edesc,eaddress,etime,edate;
        ImageView eimage;
        RelativeLayout mainLayout;

        public EventHolder(@NonNull View itemView) {
            super(itemView);

            etile = itemView.findViewById(R.id.evTitle);
            edesc = itemView.findViewById(R.id.evDesc);
            eimage = itemView.findViewById(R.id.imgEvent);
            eaddress = itemView.findViewById(R.id.evAdd);
            etime = itemView.findViewById(R.id.evTime);
            edate = itemView.findViewById(R.id.evDate);
            mainLayout = itemView.findViewById(R.id.main_layout);

        }
    }
}
