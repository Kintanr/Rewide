package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {


    EditText eml, password1;
    Button blogin;
    TextView tvcreate;
    FirebaseAuth mauth;

    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mauth = FirebaseAuth.getInstance();
        eml = findViewById(R.id.email);
        password1 = findViewById(R.id.pass);
        blogin = findViewById(R.id.login);
        tvcreate = findViewById(R.id.create);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mUser = mauth.getCurrentUser();
                if( mUser != null ){
                    Toast.makeText(login.this, "Welcome!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(login.this, menu.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(login.this, "Please Login", Toast.LENGTH_SHORT).show();
                }
            }
        };

        blogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String em =  eml.getText().toString();
                String pwd = password1.getText().toString();
                if (em.isEmpty()){
                    eml.setError("Please enter your email");
                    eml.requestFocus();
                }
                else if (pwd.isEmpty()){
                    password1.setError("Please enter your password");
                    password1.requestFocus();
                }

                else if (em.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(login.this, "Fields are empty!", Toast.LENGTH_SHORT).show();

                }
                else if (!(em.isEmpty() && pwd.isEmpty())){
                    mauth.signInWithEmailAndPassword(em,pwd).addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()){
                                Toast.makeText(login.this, "Error!, Please login again", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Intent toMenu = new Intent(login.this, menu.class);
                                startActivity(toMenu);
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(login.this, "Error!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        tvcreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(login.this, sign.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mauth.addAuthStateListener(mAuthStateListener);
    }
}
