package com.example.projectpdf;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.BreakIterator;
import java.util.ArrayList;

public class MyAdapter1 extends RecyclerView.Adapter<MyAdapter1.MyViewHolder> {


    subselect context;
    ArrayList<subhelper> list;

    public MyAdapter1(subselect context, ArrayList<subhelper> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public MyAdapter1.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sublist, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter1.MyViewHolder holder, int position) {


        subhelper user = list.get(position);
        holder.subname.setText(user.getSubname());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedText = user.getSubname();

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

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView subname;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            subname=itemView.findViewById(R.id.textbox);
        }
    }
}
