package com.example.projectpdf;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    yearSelect context;
    ArrayList<semhelper> list;

    public MyAdapter(yearSelect context, ArrayList<semhelper> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.semlist,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {


        semhelper user = list.get(position);
        holder.semno.setText(user.getSemno());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedText = user.getSemno();

                // Start a new activity and pass the selectedText as an extra
                Intent intent = new Intent(context, subselect.class);
                intent.putExtra("selectedText", selectedText);
                context.startActivity(intent);
            }
        });





    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView semno;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            semno=itemView.findViewById(R.id.textbox);
        }
    }
}
