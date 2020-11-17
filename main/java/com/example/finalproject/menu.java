package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class menu extends AppCompatActivity {

    DatabaseReference reference;
    RecyclerView mysapi;
    ArrayList<itemsapi> list;
    adapter myadapter;
    Button out;
    FirebaseAuth mauth;
    String GetUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setContentView(R.layout.activity_menu);

        mauth = FirebaseAuth.getInstance();
        out = findViewById(R.id.keluar);

        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent out = new Intent(menu.this, login.class);
                startActivity(out);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent s = new Intent(menu.this, AddSapi.class);
                startActivity(s);
            }
        });

        //inisisalisasi
        mysapi = findViewById(R.id.urutan);
        mysapi.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<itemsapi>();

        //autentikasi
        mauth = FirebaseAuth.getInstance();
        FirebaseUser user = mauth.getCurrentUser();
        GetUserID = user.getUid();

        //ambil data
        reference = FirebaseDatabase.getInstance().getReference().child("Rewide").child(GetUserID);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    itemsapi i = dataSnapshot1.getValue(itemsapi.class);
                    list.add(i);
                }
                myadapter = new adapter(menu.this, list);
                mysapi.setAdapter(myadapter);
                myadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Tidak ada data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);

        /*MenuItem searchitem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchitem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                prosescari(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                prosescari(s);
                return true;
            }
        });*/
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            Intent out = new Intent(menu.this, sign.class);
            startActivity(out);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*private void prosescari(String s) {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    itemsapi i = dataSnapshot1.getValue(itemsapi.class);
                    list.add(i);
                }
                myadapter = new adapter(menu.this, list);
                mysapi.setAdapter(myadapter);
                myadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Tidak ada data", Toast.LENGTH_SHORT).show();
            }
        });
    }*/
}
