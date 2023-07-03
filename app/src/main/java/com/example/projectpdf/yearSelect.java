package com.example.projectpdf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;


public class yearSelect extends AppCompatActivity {


    Button btlogout,btdown,feedbt;

    RecyclerView recyclerView;
    DatabaseReference databasereference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://projectpdf-9cd03-default-rtdb.firebaseio.com/");
    MyAdapter myAdapter;
    ArrayList<semhelper> list;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year_select);

        feedbt=(Button)findViewById(R.id.feedbackbt);
        btlogout=(Button)findViewById(R.id.logoutbt);
        btdown=(Button)findViewById(R.id.downbt);

        btlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("name", "False");
                editor.apply();


                Intent myIntent = new Intent(yearSelect.this,MainActivity.class);
                startActivity(myIntent);

            }
        });


        feedbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://grievances-note-stack.netlify.app/"));
                startActivity(urlIntent);
            }
        });


        btdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(yearSelect.this, viewdownloads.class);
                startActivity(myIntent);
            }
        });







        recyclerView = findViewById(R.id.recycleview123);
        databasereference = FirebaseDatabase.getInstance().getReference(  "sem");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        list= new ArrayList<>();
        myAdapter = new MyAdapter(this, list);
        recyclerView.setAdapter(myAdapter);



        databasereference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear(); // Clear the list before adding new data

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String semNo = dataSnapshot.getValue(String.class); // Retrieve the string value
                    semhelper user = new semhelper(semNo); // Create a new semhelper object
                    list.add(user);
                }

                myAdapter.notifyDataSetChanged(); // Notify the adapter about the data change
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle cancellation
            }
        });


    }
    @Override
    public void onBackPressed() {
        //Toast.makeText(this, "Back button and back gesture are disabled.", Toast.LENGTH_SHORT).show();
        // You can add additional code here if needed
    }
}