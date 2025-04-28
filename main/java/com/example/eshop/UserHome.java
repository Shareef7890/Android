package com.example.eshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserHome extends AppCompatActivity {
    Button vProducts, myCart, orders, border,bulkorders;
    ArrayList<Datacraft> count = new ArrayList<>();
    FirebaseDatabase database;
    DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        vProducts = (Button) findViewById(R.id.uViewProducts);
        getCount();



      vProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserHome.this, ViewProducts.class);
                startActivity(intent);
            }
        });
        myCart = (Button) findViewById(R.id.myCart);
        myCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UserHome.this, MyCart.class);
                startActivity(intent);
                getCount();
            }
        });
        orders = (Button) findViewById(R.id.myOrders);
        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserHome.this, MyOrders.class);
                startActivity(intent);
            }
        });
        border = (Button) findViewById(R.id.bulkOrders);
        border.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserHome.this, BulkOrders.class);
                startActivity(intent);
            }
        });
        bulkorders = (Button) findViewById(R.id.myBulkOrders);
        bulkorders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserHome.this, ViewulkO.class);
                startActivity(intent);
            }
        });
        Button logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserHome.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {

        Toast.makeText(getApplicationContext(), " click to logout", Toast.LENGTH_SHORT).show();
    }

    public ArrayList<Datacraft> getCount() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("EShop");
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String uemail =sharedPreferences.getString("email","");
        Toast.makeText(getApplicationContext(), "view=" +uemail, Toast.LENGTH_SHORT).show();
        String accFb="1234";
        myRef.child(accFb).child("tempCart").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                count.clear();
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Datacraft user = d.getValue(Datacraft.class);
                    Toast.makeText(getApplicationContext(), "ddddd=" + user.getStatus(), Toast.LENGTH_SHORT).show();

                    count.add(user);
                    TextView  ctext = (TextView) findViewById(R.id.mcartCount);
                    count.size();
                    ctext.setText(""+count.size());

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return count;
    }


}


