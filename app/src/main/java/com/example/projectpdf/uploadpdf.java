package com.example.projectpdf;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class uploadpdf extends AppCompatActivity {

    String selectedtext;
    TextView tx;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadpdf);
        tx = (TextView) findViewById(R.id.textshow);


        selectedtext = getIntent().getStringExtra("selectedsub_to_upload");
        tx.setText(selectedtext);




    }
}