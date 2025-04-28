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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UReg extends AppCompatActivity {
    EditText et1,et2,et3,et4;
    Button btn;
    DatabaseReference db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ureg);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference();

        et1=(EditText)findViewById(R.id.loginEmailU);
        et2=(EditText)findViewById(R.id.loginpassU);
        et4=(EditText)findViewById(R.id.loginCpassU);
        et3=(EditText)findViewById(R.id.loginCellU);
        btn=(Button) findViewById(R.id.regBtnU);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et1.getText().toString().isEmpty() ) {
                    Toast.makeText(UReg.this, "email should not Empty", Toast.LENGTH_SHORT).show();

                }else if(et2.getText().toString().isEmpty()) {
                    Toast.makeText(UReg.this, "password should not Empty", Toast.LENGTH_SHORT).show();

                }else if(et3.getText().toString().isEmpty()) {
                    Toast.makeText(UReg.this, "cell should not Empty", Toast.LENGTH_SHORT).show();

                }else if(et3.getText().toString().equals(et4.getText().toString())) {
                    Toast.makeText(UReg.this, "conform password mismatch", Toast.LENGTH_SHORT).show();

                }else {
                    registerNewUser();
                    Datacraft dc = new Datacraft();
                    dc.setUEmail(et1.getText().toString());
                    db.child("EShop").child("reg").push().setValue(dc);
                    Intent intent = new Intent(UReg.this, UserLogin.class);
                    startActivity(intent);
                    Toast.makeText(UReg.this, "Successfully Registered", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    private void registerNewUser()
    {
        // Take the value of two edit texts in Strings
        String email, password;
        email = et1.getText().toString();
        password = et2.getText().toString()+"UR";

        // create new user or register new user
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),"Registration success!!",Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(UReg.this,UserLogin.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Registration failed!!"+ " Please try again later",Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }
}