package com.example.projectpdf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.firebase.ui.database.FirebaseRecyclerOptions;


public class showpdf extends AppCompatActivity {


    String selectedText;
    Button uploadbt;
    RecyclerView pdfRecyclerView;
    private DatabaseReference pRef;
    Query query;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showpdf);
        uploadbt = (Button)findViewById(R.id.uploadbt);


        selectedText = getIntent().getStringExtra("selectedsub");


        uploadbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(showpdf.this,authsignin.class);
                myIntent.putExtra("selectedsub_to_upload", selectedText);
                startActivity(myIntent);
            }
        });


        displayPdfs();


    }


    private void displayPdfs() {

        pRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://projectpdf-9cd03-default-rtdb.firebaseio.com/").child(selectedText);
        pdfRecyclerView = findViewById(R.id.recycleview12345);
        pdfRecyclerView.setHasFixedSize(true);
        pdfRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        query = pRef.orderByChild("filename");
        //we will request the files with the filename, if filename exists then it will show in the recyclerView
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    progressBar.setVisibility(View.GONE);
                    showPdf();
                }else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(showpdf.this, "NO FILES FOUND", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }





    private void showPdf() {
        FirebaseRecyclerOptions<FileinModel> options = new FirebaseRecyclerOptions.Builder<FileinModel>()
                .setQuery(query, FileinModel.class)
                .build();
        FirebaseRecyclerAdapter<FileinModel, Adapter> adapter = new FirebaseRecyclerAdapter<FileinModel, Adapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull  Adapter holder, int position, @NonNull FileinModel model) {

                progressBar.setVisibility(View.GONE);
                holder.pdfTitle.setText(model.getFilename());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setType("application/pdf");
                        intent.setData(Uri.parse(model.getFileurl()));
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public Adapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pdf_item,parent,false);
                Adapter holder = new Adapter(view);
                return holder;
            }
        };

        pdfRecyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}