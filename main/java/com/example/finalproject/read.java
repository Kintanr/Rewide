package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;

import android.Manifest;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class read extends AppCompatActivity {
    TextView nm, sex, date, breed, weight, place, tib;
    Bitmap qrbit;
    Button bqr, bsave;
    ImageView kode;
    ImageButton bagi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        createNotificationChannel();


        bqr = findViewById(R.id.btnqr);
        kode = findViewById(R.id.qr);
        bagi = findViewById(R.id.share);
        bagi.setVisibility(View.INVISIBLE);

        bsave = findViewById(R.id.btnib);
        //bsave.setVisibility(View.INVISIBLE);
        //tib = findViewById(R.id.tvib);

        nm = findViewById(R.id.rinisial);
        sex = findViewById(R.id.rjk);
        date = findViewById(R.id.rtanggal);
        breed = findViewById(R.id.rketurunan);
        weight = findViewById(R.id.rberat);
        place = findViewById(R.id.rtempat);

        nm.setText(getIntent().getStringExtra("title"));
        sex.setText(getIntent().getStringExtra("sex"));
        date.setText(getIntent().getStringExtra("date"));
        breed.setText(getIntent().getStringExtra("breed"));
        weight.setText(getIntent().getStringExtra("weight"));
        place.setText(getIntent().getStringExtra("place"));




        /*final String sapikey = getIntent().getStringExtra("sapikey");
        mauth = FirebaseAuth.getInstance();
        FirebaseUser user = mauth.getCurrentUser();
        GetUserID = user.getUid();

        reference = FirebaseDatabase.getInstance().getReference().child("Rewide").child(GetUserID).child("Sapi"+ sapikey);*/


        bsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(read.this, "IB tersimpan", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(read.this, reminder.class);
                intent.putExtra("jdl",nm.toString());
                PendingIntent pendingIntent =
                        PendingIntent.getBroadcast(read.this, 0, intent, 0);

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                /*lama
                long currentTime = System.currentTimeMillis();
                //mengatur waktu untuk alarm
                long tenSeconds = 480 * 60 * 60 * 1000;*/

                //baru
                Date waktuPermulaan = new Date();

                int jmlTambahanWaktu = 20;

                // Menambah hari
                Calendar cal = Calendar.getInstance();
                cal.setTime(waktuPermulaan);
                cal.add(Calendar.DATE, jmlTambahanWaktu);

                alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        cal.getTimeInMillis(),
                        pendingIntent);
            }
        });

        bqr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                QRGEncoder qrgEncoder = new QRGEncoder("sapi: " + nm.getText().toString()
                        + ", jenis kelamin: " + sex.getText().toString()
                        + ", tanggal lahir: " + date.getText().toString()
                        + ", keturunan: " + breed.getText().toString()
                        + ", berat: " + weight.getText().toString()
                        + ", asal: " + place.getText().toString(), null, QRGContents.Type.TEXT, 3600);
                try {
                    qrbit = qrgEncoder.encodeAsBitmap();
                    kode.setImageBitmap(qrbit);
                    bagi.setVisibility(View.VISIBLE);
                    bagi.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ActivityCompat.requestPermissions(read.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                            MediaStore.Images.Media.insertImage(getContentResolver(), qrbit, "code_scanner"
                                    , null);
                            Toast.makeText(read.this, "Tersimpan di galeri", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    });

                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Rewide";
            String description = "Sapi Birahi";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("Rewide", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}

