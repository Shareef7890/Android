package com.example.eshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ViewOrder extends AppCompatActivity {
   ImageView img;
   TextView tv1,tv2,tv3,tv4,tv5;
   Button cart,conform;
    DatabaseReference db;
    String pn,fe,u;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);
        db = FirebaseDatabase.getInstance().getReference();

        img=(ImageView) findViewById(R.id.bimg);
        tv1=(TextView) findViewById(R.id.Product1);
        tv2=(TextView) findViewById(R.id.Product2);
        tv3=(TextView) findViewById(R.id.Product3);
        tv4=(TextView) findViewById(R.id.Product4);
        tv5=(TextView) findViewById(R.id.Product5);
        Bundle b=getIntent().getExtras();
         pn=b.getString("pname");
         fe=b.getString("email");
         u=b.getString("url");
        proDetail();
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proDetail();
            }
        });
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        storageRef = storage.getReferenceFromUrl(u);
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                tv1.setText(pn);


                Glide.with(getApplicationContext()).load(uri).circleCrop().into(img);



            }
        });
        cart=(Button) findViewById(R.id.addCart);
        conform=(Button) findViewById(R.id.order);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                String email = sh.getString("email", "");
                String accFB="1234";
               String fn= tv2.getText().toString();
                String ca= tv3.getText().toString();
                String pr= tv4.getText().toString();
                Datacraft dc = new Datacraft();
                dc.setFName(pn);
                dc.setPname(fn);
                dc.setCategory(ca);
                dc.setPrice(pr);
                dc.setImg(u);
                dc.setFEmail(fe);
                dc.setUEmail(email);
                dc.setAccFb(accFB);
                dc.setStatus("pending");
                db.child("EShop").child(accFB).child("cart").child(pn).push().setValue(dc);
               // db.child("EShop").child(accFB).child("myorder").push().setValue(dc);

                Datacraft dcc= new Datacraft();
                dcc.setPname(pn);           ;
                dcc.setImg(u);
                dcc.setUEmail(email);
                dcc.setFEmail(email);
                db.child("EShop").child(accFB).child("tempCart").push().setValue(dcc);
                db.child("EShop").child(accFB).child("myorder").child(pn).push().setValue(dcc);

                //and displaying a success toast
                Toast.makeText(getApplicationContext(), "Added To Cart ", Toast.LENGTH_LONG).show();

            }
        });
        conform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                String email = sh.getString("email", "");


                String accFB="1234";
                String fn= tv2.getText().toString();
                String ca= tv3.getText().toString();
                String pr= tv4.getText().toString();
                Datacraft dc = new Datacraft();
                dc.setFName(fn);
                dc.setPname(pn);
                dc.setCategory(ca);
                dc.setPrice(pr);
                dc.setImg(u);
                dc.setFEmail(fe);
                dc.setUEmail(email);
                dc.setAccFb(accFB);
                dc.setStatus("pending");
                db.child("EShop").child("order").push().setValue(dc);
                db.child("EShop").child(accFB).child("myorder").push().setValue(dc);
                Datacraft dcc = new Datacraft();
                dcc.setFName(pn);
                dcc.setUEmail(email);
                dcc.setFEmail(fe);
                dcc.setAccFb(accFB);
                dcc.setImg(u);
                dcc.setStatus("pending");

                db.child("EShop").child(accFB).child("myorder").child(pn).push().setValue(dc);
                db.child("EShop").child(accFB).child("myorders").push().setValue(dcc);

                //and displaying a success toast
                Toast.makeText(getApplicationContext(), "placed order ", Toast.LENGTH_LONG).show();

            }
        });
    }

    public void proDetail(){
        FirebaseDatabase  database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("EShop");
        Toast.makeText(getApplicationContext(), "ddddd=" +pn, Toast.LENGTH_SHORT).show();
        myRef.child("products").child(pn).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot appleSnapshot: snapshot.getChildren()) {
                    Datacraft user = appleSnapshot.getValue(Datacraft.class);
                    String s=   user.getStatus();
                    String ca=   user.getCategory();
                    String price=   user.getPrice();
                    String fn=user.getFName();
                    tv2.setText(fn);
                    tv3.setText(ca);
                    tv4.setText(price);
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