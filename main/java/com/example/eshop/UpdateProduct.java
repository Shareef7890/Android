package com.example.eshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UpdateProduct extends AppCompatActivity {
    ListView lv;
    Button btn;
    UpdateAdapter adapter;
    ArrayList<Datacraft> listI=new ArrayList<>();
    FirebaseDatabase database;
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);
        lv = findViewById(R.id.proDel);

        adapter = new UpdateAdapter(UpdateProduct.this, getList());

        lv.setAdapter(adapter);

        btn = (Button) findViewById(R.id.refreshDel);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lv.setAdapter(adapter);
            }
        });
    } public ArrayList<Datacraft> getList() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("EShop");

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String email = sh.getString("email", "");
        myRef.child("items").orderByChild("femail").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listI.clear();
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Datacraft user = d.getValue(Datacraft.class);
                     Toast.makeText(getApplicationContext(), "ddddd=" +user.getFEmail(), Toast.LENGTH_SHORT).show();

                   listI.add(user);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return listI;
    }
}