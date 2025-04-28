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

public class ViewUOrders extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference myRef;
    ArrayList<Datacraft> items = new ArrayList<>();
    ListView lv;
    Button btn;
    VUOAdapter  adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_uorders);
        lv = findViewById(R.id.list_UO);

        adapter = new VUOAdapter(ViewUOrders.this,getItems());

        lv.setAdapter(adapter);
        btn = (Button) findViewById(R.id.refresh_UO);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lv.setAdapter(adapter);
            }
        });
    }
    public ArrayList<Datacraft> getItems() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("EShop");
        //Toast.makeText(getApplicationContext(), "view=" +rsname+iname, Toast.LENGTH_SHORT).show();
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String email = sh.getString("email", "");
        myRef.child("orders").orderByChild("femail").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                items.clear();
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Datacraft user = d.getValue(Datacraft.class);
                    Toast.makeText(getApplicationContext(), "ddddd=" + user.getFName(), Toast.LENGTH_SHORT).show();

                    items.add(user);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return items;
    }
}