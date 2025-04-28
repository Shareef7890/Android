package com.example.eshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FReg extends AppCompatActivity {

    EditText et1, et2, et3,et4;
    Button btn;
    DatabaseReference db;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freg);
        db = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        et1=(EditText)findViewById(R.id.loginEmailR);
        et2=(EditText)findViewById(R.id.loginpassR);
        et4=(EditText) findViewById(R.id.loginCpassR);
        et3=(EditText)findViewById(R.id.loginCellR);
        btn=(Button) findViewById(R.id.regBtnR);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et1.getText().toString().isEmpty() ) {
                    Toast.makeText(FReg.this, "email should not Empty", Toast.LENGTH_SHORT).show();

                }else if(et2.getText().toString().isEmpty()) {
                    Toast.makeText(FReg.this, "password should not Empty", Toast.LENGTH_SHORT).show();

                }else if(et3.getText().toString().isEmpty()) {
                    Toast.makeText(FReg.this, "cell should not Empty", Toast.LENGTH_SHORT).show();

                }else if(et3.getText().toString().equals(et4.getText().toString())) {
                     Toast.makeText(FReg.this, "conform password mismatch", Toast.LENGTH_SHORT).show();

                }else {
                    registerNewUser();
                    Datacraft dc = new Datacraft();
                    dc.setFEmail(et1.getText().toString());
                    db.child("EShop").child("reg").push().setValue(dc);
                    Intent intent = new Intent(FReg.this, FarmerActivity.class);
                    startActivity(intent);
                    Toast.makeText(FReg.this, "Successfully Registered", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    private void registerNewUser()
    {
        // Take the value of two edit texts in Strings
        String email, password;
        email = et1.getText().toString();
        password = et2.getText().toString()+"FR";

        // create new user or register new user
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),"Registration success!!",Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(FReg.this,FarmerActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Registration failed!!"+ " Please try again later",Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }
}