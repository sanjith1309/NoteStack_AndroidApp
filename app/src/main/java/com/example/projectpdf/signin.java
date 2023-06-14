package com.example.projectpdf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class signin extends AppCompatActivity {


    Button backToSignIn,signUp;
    String rollno;
    String password;
    String email;
    String name;
    EditText editname,editemail,editpassword,editrollno;

    DatabaseReference databasereference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://projectpdf-9cd03-default-rtdb.firebaseio.com/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        backToSignIn = (Button) findViewById(R.id.button2);
        editname=(EditText)findViewById(R.id.editTextTextPersonName5);
        editemail=(EditText)findViewById(R.id.editTextTextPersonName3);
        editpassword=(EditText)findViewById(R.id.editTextTextPassword2);
        editrollno=(EditText)findViewById(R.id.editTextTextPersonName4);
        signUp=(Button)findViewById(R.id.button);

        backToSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(signin.this, MainActivity.class);
                startActivity(myIntent);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });



    }

    void register(){

        name=editname.getText().toString().trim();
        rollno=editrollno.getText().toString().trim();
        email=editemail.getText().toString().trim();
        password=editpassword.getText().toString().trim();
        if(name.isEmpty()){
            editname.setError("Please enter the name.");
            editname.requestFocus();
            return;
        }
        else if(rollno.isEmpty()){
            editrollno.setError("Please enter the roll number.");
            editrollno.requestFocus();
            return;
        }
        else if(email.isEmpty()){
            editemail.setError("Please enter the Email");
            editemail.requestFocus();
            return;
        }
        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editemail.setError("Please enter a valid email");
            editemail.requestFocus();
            return;
        }
        else if(password.isEmpty()){
            editpassword.setError("Please enter the password.");
            editpassword.requestFocus();
            return;
        }
        else if(password.length()<8){
            editpassword.setError("length of the pass word must be greater than 8");
            editpassword.requestFocus();
            return;
        }

        else{

            databasereference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild(rollno)) {
                        Toast.makeText(signin.this, "The rollno. is already registered", Toast.LENGTH_SHORT).show();
                    } else {
                        // sending data to database.
                        databasereference.child("Users").child(rollno).setValue(new helper1(name,rollno,email,password));
                        Toast.makeText(signin.this, "User is registered sucessfully", Toast.LENGTH_SHORT).show();
                        Intent myIntent = new Intent(signin.this, MainActivity.class);
                        startActivity(myIntent);

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }
    }


}