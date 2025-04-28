package com.example.eshop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

public class ProductDetails extends AppCompatActivity {
    ImageView img;
    TextView tv1,tv2,tv3,tv4;
    Button back,delete;
    DatabaseReference db;
    String pn,n,c,p,u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
    }
}