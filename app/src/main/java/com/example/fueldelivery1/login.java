package com.example.fueldelivery1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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

public class login extends AppCompatActivity {


    EditText rPhoneNumberLogin,rPasswordLogin;
    TextView rlnkRegister;
    Button rbtnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if(userSharedPreference.getUserName(login.this).length() == 0) {

            setContentView(R.layout.activity_login);

            rPhoneNumberLogin = findViewById(R.id.PhoneNumberLogin);
            rPasswordLogin = findViewById(R.id.PasswordLogin);
            rbtnLogin = findViewById(R.id.btnLogin);
            rlnkRegister = findViewById(R.id.lnkRegister);

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference table_user = database.getReference("user");

            rbtnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (rPhoneNumberLogin.getText().length() == 0) {
                        rPhoneNumberLogin.setError("Feild cannot be left blank");
                    } else if (rPasswordLogin.getText().length() == 0) {
                        rPasswordLogin.setError("Feild cannot be left blank");
                    } else {
                        final ProgressDialog mDialog = new ProgressDialog(login.this);
                        mDialog.setMessage("Logging In...");
                        mDialog.show();
                        table_user.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.child(rPhoneNumberLogin.getText().toString()).exists()) {
                                    mDialog.dismiss();
                                    user user = dataSnapshot.child(rPhoneNumberLogin.getText().toString()).getValue(user.class);
                                    assert user != null;
                                    if (user.getPassword().equals(rPasswordLogin.getText().toString())) {
                                        userSharedPreference.setUserName(login.this,rPhoneNumberLogin.getText().toString());
                                        Intent homepage = new Intent(login.this, MapsActivity.class);
                                        startActivity(homepage);
                                    } else {
                                        Toast.makeText(login.this, "Sign in failed", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    mDialog.dismiss();
                                    Toast.makeText(login.this, "User does not Exist", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            });

            rlnkRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent reg = new Intent(login.this,register.class);
                    startActivity(reg);
                }
            });
        }
        else
        {
            Intent about = new Intent(login.this, about.class);
            about.putExtra("type","Login");
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
                Intent login=new Intent(login.this,login.class);
                startActivity(login);
                return true;

            case R.id.home_item:
                Intent home = new Intent(login.this,MapsActivity.class);
                startActivity(home);
                return true;

            case R.id.prev_orders:
                Intent prev = new Intent(login.this,prevOrder.class);
                startActivity(prev);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
