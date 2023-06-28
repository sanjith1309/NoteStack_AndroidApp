package com.example.projectpdf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

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

public class subselect extends AppCompatActivity {


    String selectedText;

    RecyclerView recyclerView;
    DatabaseReference databasereference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://projectpdf-9cd03-default-rtdb.firebaseio.com/");
    MyAdapter1 myAdapter;
    ArrayList<subhelper> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subselect);

        selectedText = getIntent().getStringExtra("selectedText");
        Log.i("qwertyuiopsdfghjk",selectedText);





        recyclerView = findViewById(R.id.recycleview1234);
        databasereference = FirebaseDatabase.getInstance().getReference(selectedText);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        list= new ArrayList<>();
        myAdapter = new MyAdapter1(this, list);
        recyclerView.setAdapter(myAdapter);



        databasereference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear(); // Clear the list before adding new data

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String subname = dataSnapshot.getValue(String.class); // Retrieve the string value
                    subhelper user = new subhelper(subname); // Create a new semhelper object
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
}