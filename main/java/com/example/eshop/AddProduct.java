package com.example.eshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Random;

public class AddProduct extends AppCompatActivity {
    EditText et1,et2,et3,et4;
    ImageView img;
    Button btn;
    // Uri indicates, where the image will be picked from
    private Uri filePath;
    // request code
    private final int PICK_IMAGE_REQUEST = 22;
    // instance for firebase storage and StorageReference
    FirebaseStorage storage;
    StorageReference storageReference;
    DatabaseReference db;
    String otp;

    String st1,st2,st3,st4;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        et1=(EditText) findViewById(R.id.pname);
        et2=(EditText) findViewById(R.id.fname);
        et3=(EditText) findViewById(R.id.pcategory);
        et4=(EditText) findViewById(R.id.pprice);
        db = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        btn=(Button)findViewById(R.id.uploadData);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                st1=et1.getText().toString();
                st2=et2.getText().toString();
                st3=et3.getText().toString();
                st4=et4.getText().toString();
                uploadImage();
            }
        });
        img=(ImageView) findViewById(R.id.pimage);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage();
            }
        });
    }
    private void SelectImage()
    {
        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            //slected file data
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                img.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void uploadImage() {

        //if there is a file to upload
        if (filePath != null) {
            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();
            Random rd=new Random();
            otp = new String();
            for(int i=0 ; i < 10 ; i++) {
                otp += rd.nextInt(10);
            }
            SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
            String email = sh.getString("email", "");
            String id = sh.getString("pass", "");
            storageReference = storageReference.child("eshop/images").child(otp+"pic.jpg");
            storageReference.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //if the upload is successfull
                            //hiding the progress dialog
                           String ss=   taskSnapshot.getUploadSessionUri().getPath();
                            Datacraft dc = new Datacraft();

                            dc.setId(id);
                            dc.setFEmail(email);
                            dc.setFName(st1);
                            dc.setPname(st2);
                            dc.setCategory(st3);
                            dc.setPrice(st4);
                            dc.setStatus("Available");
                            dc.setImg("gs://eshop-3a8d8.appspot.com/eshop/images/"+otp+"pic.jpg");
                            db.child("EShop").child("products").child(st1).push().setValue(dc);

                            Datacraft dcc = new Datacraft();
                            dcc.setFEmail(email);
                            dcc.setFName(st1);
                            dcc.setPname(st2);
                            dcc.setCategory(st3);
                            dcc.setPrice(st4);
                            dc.setStatus("Available");
                            dcc.setImg("gs://eshop-3a8d8.appspot.com/eshop/images/"+otp+"pic.jpg");
                           db.child("EShop").child("items").push().setValue(dcc);

                            progressDialog.dismiss();

                            //and displaying a success toast
                            Toast.makeText(getApplicationContext(), "product Uploaded "+ss, Toast.LENGTH_LONG).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //if the upload is not successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();

                            //and displaying error message
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //calculating progress percentage
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                            //displaying percentage in progress dialog
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
        }
        //if there is not any file
        else {
            //you can display an error toast
        }
    }
}