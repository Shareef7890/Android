package com.example.eshop;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FConformation extends AppCompatActivity {
    ImageView img;
    TextView tv1,tv2,tv3,tv4;
    Button cart,conform;
    DatabaseReference db;
    String pn,n,c,p,u;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fconformation);
        db = FirebaseDatabase.getInstance().getReference();

        img=(ImageView) findViewById(R.id.fbimg);
        tv1=(TextView) findViewById(R.id.FProduct1);
        tv2=(TextView) findViewById(R.id.FProduct2);
        tv3=(TextView) findViewById(R.id.FProduct3);
        tv4=(TextView) findViewById(R.id.FProduct4);
        Bundle b=getIntent().getExtras();
        pn=b.getString("pname");
        n=b.getString("name");
        c=b.getString("category");
        p=b.getString("price");
        u=b.getString("url");

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
        cart=(Button) findViewById(R.id.FCorm);
        conform=(Button) findViewById(R.id.Freject);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                String email = sh.getString("email", "");

                Datacraft dc = new Datacraft();
                dc.setFName(pn);
                dc.setPname(n);
                dc.setCategory(c);
                dc.setPrice(p);
                dc.setImg(u);
                dc.setCartN(email);
                // dc.setImg(ss);
                db.child("EShop").child("conform").push().setValue(dc);
                //and displaying a success toast
                Toast.makeText(getApplicationContext(), "Order conform ", Toast.LENGTH_LONG).show();

            }
        });
        conform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                String email = sh.getString("email", "");

                Datacraft dc = new Datacraft();
                dc.setFName(pn);
                dc.setPname(n);
                dc.setCategory(c);
                dc.setPrice(p);
                dc.setImg(u);
                dc.setCartN(email);
                dc.setStatus("reject");
                // dc.setImg(ss);
                db.child("EShop").child("reject").push().setValue(dc);
                //and displaying a success toast
                Toast.makeText(getApplicationContext(), " order rejected ", Toast.LENGTH_LONG).show();

            }
        });
    }
}