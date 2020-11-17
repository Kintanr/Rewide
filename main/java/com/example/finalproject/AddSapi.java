package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import android.Manifest;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Random;

public class AddSapi extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    EditText nm, sex, breed, weight, place;
    TextView date;
    Button save;
    String GetUserID;
    ImageView upload;

    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 101;

    private static final int IMAGE_PICK_CAMERA = 102;
    private static final int IMAGE_PICK_GALLERY = 103;

    private String[] cameraPermission;
    private String[] storagePermission;

    Uri imageUri;

    DatabaseReference reference;
    FirebaseAuth mauth;
    //StorageReference storage;
    //StorageTask uptask;

    Integer sapinum = new Random().nextInt();
    String sapikey = Integer.toString(sapinum);

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String cdate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(c.getTime());

        date = findViewById(R.id.tanggal);
        date.setText(cdate);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sapi);

        nm = findViewById(R.id.inisial);
        sex = findViewById(R.id.jk);
        date = findViewById(R.id.tanggal);
        breed = findViewById(R.id.keturunan);
        weight = findViewById(R.id.berat);
        place = findViewById(R.id.tempat);
        save = findViewById(R.id.simpan);
        //upload = findViewById(R.id.addfoto);

        /*cameraPermission = new  String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE};*/

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datepick = new DatePickerFragment();
                datepick.show(getSupportFragmentManager(), "date picker");
            }
        });


        //storage = FirebaseStorage.getInstance().getReference().child("Rewide");

        /*
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bukafile();
            }
        });*/

        mauth = FirebaseAuth.getInstance();
        FirebaseUser user = mauth.getCurrentUser();
        GetUserID = user.getUid();

        reference = FirebaseDatabase.getInstance().getReference().child("Rewide").child(GetUserID).child("Sapi" + sapinum);


        //reference = FirebaseDatabase.getInstance().getReference().child("Rewide").child("Sapi" + sapinum);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        //kode untuk berubah atau maenambahkan
                        dataSnapshot.getRef().child("title").setValue(nm.getText().toString());
                        dataSnapshot.getRef().child("sex").setValue(sex.getText().toString());
                        dataSnapshot.getRef().child("date").setValue(date.getText().toString());
                        dataSnapshot.getRef().child("breed").setValue(breed.getText().toString());
                        dataSnapshot.getRef().child("weight").setValue(weight.getText().toString());
                        dataSnapshot.getRef().child("place").setValue(place.getText().toString());
                        dataSnapshot.getRef().child("sapikey").setValue(sapikey);
                        //upgambar();

                        Intent i = new Intent(AddSapi.this, menu.class);
                        startActivity(i);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });


    }
    /*
    private void upgambar() {
        if(imageUri != null){
            StorageReference filestorage = storage.child(getFileExtension(imageUri));

            uptask = filestorage.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    reference.child("gambar").setValue(taskSnapshot.getUploadSessionUri().toString());
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }else {
            Toast.makeText(this,"Tidak ada file yang dipilih", Toast.LENGTH_SHORT).show();
        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    private void Bukafile() {
        String[] options = {"Camera","Gallery"};

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Select for image");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which==0){
                    //open camera
                    if (!checkCameraPermission()){
                        requestCameraPermission();
                    } else{
                        pickFromCamera();
                    }
                }
                else if (which==1){
                    if (!checkStoragePermission()){
                        requestStoragePermission();
                    } else {
                        pickFromStorage();
                    }
                }
            }
        });
        builder.create().show();
    }


    private void pickFromStorage() {

        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, IMAGE_PICK_GALLERY);
    }

    private void pickFromCamera() {

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"Image title");
        values.put(MediaStore.Images.Media.DESCRIPTION,"Image description");

        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent,IMAGE_PICK_CAMERA);
    }

    private boolean checkStoragePermission(){
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);

        return result;
    }

    private void requestStoragePermission(){
        ActivityCompat.requestPermissions(this, storagePermission, STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermission(){
        boolean result = ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)
                == (PackageManager.PERMISSION_GRANTED);

        boolean result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);

        return result && result1;
    }

    private void requestCameraPermission(){
        ActivityCompat.requestPermissions(this,cameraPermission, CAMERA_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case CAMERA_REQUEST_CODE:{
                if (grantResults.length>0){
                    boolean cameraAccepted = grantResults[0] ==   PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (cameraAccepted && storageAccepted){
                        pickFromCamera();
                    }
                    else {
                        Toast.makeText(this,"Camera permission required!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;

            case STORAGE_REQUEST_CODE: {
                if (grantResults.length>0){
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (storageAccepted){
                        pickFromStorage();
                    }
                    else {
                        Toast.makeText(this,"Storage permission required!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK){
            if (requestCode==IMAGE_PICK_GALLERY){
                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(this);
            }
            else if (requestCode == IMAGE_PICK_CAMERA){
                CropImage.activity(imageUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(this);
            }
            else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
                CropImage.ActivityResult result = CropImage.getActivityResult(data);

                if (resultCode == RESULT_OK){
                    Uri resultUri = result.getUri();
                    imageUri = resultUri;
                    upload.setImageURI(resultUri);
                }
                else if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                    Exception error = result.getError();
                    Toast.makeText(this, ""+error,Toast.LENGTH_SHORT).show();
                }
            }

        }

        super.onActivityResult(requestCode, resultCode, data);

    }*/

}
