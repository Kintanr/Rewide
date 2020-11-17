package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class sign extends AppCompatActivity {
    EditText email, password;
    Button btnSign;
    FirebaseAuth mauth;
    DatabaseReference reference;
    String GetUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        mauth = FirebaseAuth.getInstance();
        email = findViewById(R.id.semail);
        password = findViewById(R.id.spass);
        btnSign = findViewById(R.id.signup);

        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*FirebaseUser user = mauth.getCurrentUser();
                GetUserID = user.getUid();

                reference = FirebaseDatabase.getInstance().getReference().child("Rewide").child(GetUserID);*/

                String em, pwd;
                em =  email.getText().toString();
                pwd = password.getText().toString();

                if (em.isEmpty()){
                    email.setError("Please enter your email");
                    email.requestFocus();
                }
                else if (pwd.isEmpty()){
                    password.setError("Please enter your password");
                    password.requestFocus();
                }

                else if (em.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(sign.this, "Fields are empty!", Toast.LENGTH_SHORT).show();

                }
                else if (!(em.isEmpty() && pwd.isEmpty())){
                    mauth.createUserWithEmailAndPassword(em,pwd).addOnCompleteListener(sign.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()){
                                Toast.makeText(sign.this, "Sign Up Unsuccessful!, Please Try Again", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                startActivity(new Intent(sign.this, menu.class));
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(sign.this, "Error!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
