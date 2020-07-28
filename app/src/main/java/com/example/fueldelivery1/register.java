package com.example.fueldelivery1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fueldelivery1.model.user;
import com.example.fueldelivery1.utils.userSharedPreference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class register extends AppCompatActivity {

    EditText rFullName,rEmail,rPhoneNumber,rPassword;
    Button rbtnRegister;
    TextView rlnknLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(userSharedPreference.getUserName(register.this).length() == 0) {
            setContentView(R.layout.activity_register);

            rFullName = findViewById(R.id.FullName);
            rEmail = findViewById(R.id.Email);
            rPhoneNumber = findViewById(R.id.PhoneNumber);
            rPassword = findViewById(R.id.Password);
            rbtnRegister = findViewById(R.id.btnRegister);
            rlnknLogin = findViewById(R.id.lnkLogin);

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference table_user = database.getReference("user");

            rbtnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (rFullName.getText().length() == 0) {
                        rFullName.setError("Feild cannot be left blank");
                    } else if (rEmail.getText().length() == 0) {
                        rEmail.setError("Feild cannot be left blank");
                    } else if (rPhoneNumber.getText().length() == 0) {
                        rPhoneNumber.setError("Feild cannot be left blank");
                    } else if (rPassword.getText().length() == 0) {
                        rPassword.setError("Feild cannot be left blank");
                    } else {
                        table_user.addValueEventListener(new ValueEventListener() {

                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.child(rPhoneNumber.getText().toString()).exists()) {
                                    Toast.makeText(register.this, "Phone Number already exsits", Toast.LENGTH_LONG).show();
                                } else {
                                    user user = new user(rFullName.getText().toString(), rEmail.getText().toString(), rPhoneNumber.getText().toString(), rPassword.getText().toString());
                                    table_user.child(rPhoneNumber.getText().toString()).setValue(user);
                                    userSharedPreference.setUserName(register.this, rPhoneNumber.getText().toString());
                                    Intent homepage = new Intent(register.this, MapsActivity.class);
                                    startActivity(homepage);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }

                        });
                    }
                }
            });

            rlnknLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent login = new Intent(register.this, login.class);
                    startActivity(login);
                }
            });
        }
        else
        {
            Intent about = new Intent(register.this, about.class);
            about.putExtra("type","Register");
            startActivity(about);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.other_activities_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.login_item:
                Intent login=new Intent(register.this,login.class);
                startActivity(login);
                return true;

            case R.id.home_item:
                Intent home = new Intent(register.this,MapsActivity.class);
                startActivity(home);
                return true;

            case R.id.prev_orders:
                Intent prev = new Intent(register.this,prevOrder.class);
                startActivity(prev);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
