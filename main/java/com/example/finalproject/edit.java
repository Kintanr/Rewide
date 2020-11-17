package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;

public class edit extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    EditText nm, sex, breed, weight, place;
    TextView date;
    Button update, del;
    String GetUserID;
    FirebaseAuth mauth;

    DatabaseReference reference;

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String cdate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(c.getTime());

        date = findViewById(R.id.etanggal);
        date.setText(cdate);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        nm = findViewById(R.id.einisial);
        sex = findViewById(R.id.ejk);
        date = findViewById(R.id.etanggal);
        breed = findViewById(R.id.eketurunan);
        weight = findViewById(R.id.eberat);
        place = findViewById(R.id.etempat);
        update = findViewById(R.id.eupdate);
        del = findViewById(R.id.edelete);


        //ambil nilai
        nm.setText(getIntent().getStringExtra("title"));
        sex.setText(getIntent().getStringExtra("sex"));
        date.setText(getIntent().getStringExtra("date"));
        breed.setText(getIntent().getStringExtra("breed"));
        weight.setText(getIntent().getStringExtra("weight"));
        place.setText(getIntent().getStringExtra("place"));



        final String sapikey = getIntent().getStringExtra("sapikey");
        mauth = FirebaseAuth.getInstance();
        FirebaseUser user = mauth.getCurrentUser();
        GetUserID = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("Rewide").child(GetUserID).child("Sapi"+ sapikey);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date.setText("");
                DialogFragment datepick = new DatePickerFragment();
                datepick.show(getSupportFragmentManager(), "date picker");
            }
        });

        //event buat delete
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Intent i = new Intent(edit.this, menu.class);
                            startActivity(i);
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Fail",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        //event buat update
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("title").setValue(nm.getText().toString());
                        dataSnapshot.getRef().child("sex").setValue(sex.getText().toString());
                        dataSnapshot.getRef().child("date").setValue(date.getText().toString());
                        dataSnapshot.getRef().child("breed").setValue(breed.getText().toString());
                        dataSnapshot.getRef().child("weight").setValue(weight.getText().toString());
                        dataSnapshot.getRef().child("place").setValue(place.getText().toString());
                        dataSnapshot.getRef().child("sapikey").setValue(sapikey);

                        Intent i = new Intent(edit.this, menu.class);
                        startActivity(i);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}