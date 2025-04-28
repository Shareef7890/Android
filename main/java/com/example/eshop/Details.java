package com.example.eshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Details extends AppCompatActivity {
    ImageView img;
    TextView tv1,tv2,tv3,tv4,tv5;
    Button Active,InActive,back;
    DatabaseReference db;
    String pn,n,c,p,u;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        db = FirebaseDatabase.getInstance().getReference();

        img=(ImageView) findViewById(R.id.bimgdel);
        tv1=(TextView) findViewById(R.id.delProduct1);
        tv2=(TextView) findViewById(R.id.delProduct2);
        tv3=(TextView) findViewById(R.id.delProduct3);
        tv4=(TextView) findViewById(R.id.delProduct4);
        tv5=(TextView) findViewById(R.id.delProduct5);
        Bundle b=getIntent().getExtras();
        pn=b.getString("pname");
        n=b.getString("fname");
        c=b.getString("category");
        p=b.getString("price");
        u=b.getString("url");
        status();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        storageRef = storage.getReferenceFromUrl(u);
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                tv1.setText(pn);
                tv2.setText(n);
                tv3.setText(c);
                tv4.setText(p);
                Glide.with(getApplicationContext()).load(uri).circleCrop().into(img);
            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status();
            }
        });
        back=(Button) findViewById(R.id.back);
        Active=(Button) findViewById(R.id.Active);
        InActive=(Button) findViewById(R.id.InActive);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Details.this, UpdateProduct.class);
                startActivity(intent);
            }
        });
        InActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delProduct();
            }
        });
    }
    public void delProduct(){

        FirebaseDatabase  database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("EShop");
        Toast.makeText(getApplicationContext(), "ddddd=" +pn, Toast.LENGTH_SHORT).show();
        myRef.child(pn).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot appleSnapshot: snapshot.getChildren()) {
                    String key = appleSnapshot.getKey();
                    appleSnapshot.getRef().removeValue();
                }

                Datacraft dcc = new Datacraft();
                dcc.setPname(pn);
                dcc.setImg(u);
                dcc.setStatus("Product Not Available");
                db = FirebaseDatabase.getInstance().getReference();
                db.child("EShop").child("products").child(pn).setValue("");
                db.child("EShop").child("products").child(pn).push().setValue(dcc);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void status(){

        FirebaseDatabase  database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("EShop");
        Toast.makeText(getApplicationContext(), "ddddd=" +pn, Toast.LENGTH_SHORT).show();
        myRef.child("products").child(pn).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot appleSnapshot: snapshot.getChildren()) {
                    Datacraft user = appleSnapshot.getValue(Datacraft.class);
                    String s=   user.getStatus();
                    tv5.setText(s);
                    Toast.makeText(getApplicationContext(), "sss=" +s, Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}