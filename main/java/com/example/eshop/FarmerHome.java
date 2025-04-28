package com.example.eshop;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class FarmerHome extends AppCompatActivity {
    Button addP,viewP,viewMo,logout;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_home);

      Button  del=(Button) findViewById(R.id.delProducts);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FarmerHome.this, UpdateProduct.class);
                startActivity(intent);
            }
        });

        addP=(Button) findViewById(R.id.addProducts);
        addP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FarmerHome.this, AddProduct.class);
                startActivity(intent);
            }
        });
        viewP=(Button) findViewById(R.id.viewOrders);
        viewP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FarmerHome.this, ViewUOrders.class);
                startActivity(intent);
            }
        });
        viewMo=(Button) findViewById(R.id.viewMultiO);
        viewMo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FarmerHome.this, ViewUMOrders.class);
                startActivity(intent);
            }
        });
        logout=(Button) findViewById(R.id.flogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FarmerHome.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {

        Toast.makeText(getApplicationContext(), " click to logout", Toast.LENGTH_SHORT).show();
    }
}