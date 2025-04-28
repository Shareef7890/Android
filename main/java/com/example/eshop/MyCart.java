package com.example.eshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyCart extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference myRef;
    DatabaseReference db;
    ArrayList<Datacraft> mycart=new ArrayList<>();
    ListView lv;
    VproductsAdapter adapter;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);
        lv = findViewById(R.id.listCart);
        btn=(Button) findViewById(R.id.refreshCart);
        db = FirebaseDatabase.getInstance().getReference();
       // helper = new FirebaseHelper(db);
        adapter = new VproductsAdapter(MyCart.this, getMyCart());

        lv.setAdapter(adapter);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lv.setAdapter(adapter);
            }
        });

    }
    public ArrayList<Datacraft> getMyCart() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("EShop");
        //Toast.makeText(getApplicationContext(), "view=" +rsname+iname, Toast.LENGTH_SHORT).show();
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String email = sh.getString("email", "");
        String accFb="1234";
        myRef.child(accFb).child("tempCart").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mycart.clear();
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Datacraft user = d.getValue(Datacraft.class);
                    // Toast.makeText(getApplicationContext(), "ddddd=" +user.getRestaurant(), Toast.LENGTH_SHORT).show();

                    mycart.add(user);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return mycart;
    }
}