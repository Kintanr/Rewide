package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 3000;

    Animation topanim, bottomanim;
    ImageView img;
    TextView t1, t2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //animasi
        topanim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomanim= AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        img= findViewById(R.id.logo);
        t1= findViewById(R.id.Teks1);
        t2= findViewById(R.id.Teks2);

        img.setAnimation(topanim);
        t1.setAnimation(bottomanim);
        t2.setAnimation(bottomanim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this, login.class);
                startActivity(i);
            }
        }, SPLASH_SCREEN);
    }
}
