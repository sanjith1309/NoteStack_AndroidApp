package com.example.projectpdf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {


    TextView tx;
    Button btlogin;
    EditText rollnotxt,passwordtxt;

    DatabaseReference databasereference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://projectpdf-9cd03-default-rtdb.firebaseio.com/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tx = (TextView) findViewById(R.id.textView);
        rollnotxt = (EditText)findViewById(R.id.editTextTextPersonName4);
        passwordtxt = (EditText)findViewById(R.id.editTextTextPassword2);
        btlogin=(Button)findViewById(R.id.button3);


        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String myname = sharedPreferences.getString("name", " ");

        if(myname.equals("True")){
            Intent i =new Intent(MainActivity.this,yearSelect.class);
            startActivity(i);
        }


        tx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(MainActivity.this, signin.class);
                startActivity(myIntent);

            }
        });
        btlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number1=rollnotxt.getText().toString().trim();
                String password1=passwordtxt.getText().toString().trim();
                if(number1.isEmpty()){
                    rollnotxt.setError("Please enter the roll number.");
                    rollnotxt.requestFocus();
                    return;
                }

                else if(password1.isEmpty()){
                    passwordtxt.setError("Please enter the password.");
                    passwordtxt.requestFocus();
                    return;
                }
                else if(password1.length()<8){
                    passwordtxt.setError("length of the pass word must be greater than 8");
                    passwordtxt.requestFocus();
                    return;
                }
                else{
                    databasereference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(number1)){
                                final String getpassword= snapshot.child(number1).child("editpassword").getValue(String.class);
                                if(getpassword.equals(password1)){

                                    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("name", "True");
                                    editor.apply();


                                    Toast.makeText(MainActivity.this, "Successfully logged in......", Toast.LENGTH_SHORT).show();
                                    Intent a  =new Intent(MainActivity.this,yearSelect.class);
                                    startActivity(a);
                                }
                                else{
                                    Toast.makeText(MainActivity.this, "Wrong password......", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(MainActivity.this, "Wrong password......", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });//data base checking...
                }
            }
        });





    }
}