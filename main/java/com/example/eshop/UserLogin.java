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

import java.util.ArrayList;

public class UserLogin extends AppCompatActivity {
    Button lBtn;
    EditText un,pwd;
    FirebaseDatabase database;
    DatabaseReference myRef;
    DatabaseReference db;
    TextView tv;
    ArrayList<Datacraft> data=new ArrayList<>();
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        mAuth = FirebaseAuth.getInstance();
        tv=(TextView) findViewById(R.id.ureg);
        un=(EditText) findViewById(R.id.uloginEmail);
        pwd=(EditText) findViewById(R.id.uloginpass);
        lBtn=(Button) findViewById(R.id.uloginBtn);
        db = FirebaseDatabase.getInstance().getReference();
        lBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (un.getText().toString().isEmpty() && pwd.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(),"UserName & Password Mismatch",Toast.LENGTH_LONG).show();
                } else{
                    getLogin();
                    String sEmail= un.getText().toString();
                    SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    myEdit.putString("email", sEmail);
                    myEdit.apply();
                    getLogin();


            }
            }
        });
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserLogin.this, UReg.class);
                startActivity(intent);
            }
        });
    }
    public void getLogin() {

        // Take the value of two edit texts in Strings
        String email, password;
        email = un.getText().toString();
        password = pwd.getText().toString()+"UR";
        // create new user or register new user
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(UserLogin.this,UserHome.class);
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
