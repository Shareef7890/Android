package com.example.eshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class FarmerActivity extends AppCompatActivity {
    Button lBtn;
    EditText un,pwd;
    FirebaseDatabase database;
    DatabaseReference myRef;
    TextView tv;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer);
        mAuth = FirebaseAuth.getInstance();
        tv=(TextView)findViewById(R.id.fReg);
        un=(EditText) findViewById(R.id.loginEmail);
        pwd=(EditText) findViewById(R.id.loginpass);
        lBtn=(Button) findViewById(R.id.loginBtn);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FarmerActivity.this, FReg.class);
                startActivity(intent);
            }
        });
        lBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (un.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Password Mismatch",Toast.LENGTH_LONG).show();
                } else if(pwd.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Password Mismatch",Toast.LENGTH_LONG).show();

                }else {
                    String sEmail= un.getText().toString();
                    String pass= pwd.getText().toString();
                    SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    myEdit.putString("email", sEmail);
                    myEdit.putString("pass", pass);
                    myEdit.apply();
                    Intent intent = new Intent(FarmerActivity.this,FarmerHome.class);
                    startActivity(intent);
                }
            }
        });
    }
    public void getLogin() {

        // Take the value of two edit texts in Strings
        String email, password;
        email = un.getText().toString();
        password = pwd.getText().toString()+"FR";
        // create new user or register new user
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(FarmerActivity.this,FarmerHome.class);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(),"Login success!!",Toast.LENGTH_LONG).show();

                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Login failed!!"+ " Please try again later",Toast.LENGTH_LONG).show();

                        }
                    }
                });

    }
}
