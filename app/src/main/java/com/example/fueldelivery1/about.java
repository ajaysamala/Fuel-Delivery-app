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

import com.example.fueldelivery1.model.user;
import com.example.fueldelivery1.utils.userSharedPreference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class about extends AppCompatActivity {

    EditText rFullNameAbout,rEmailAbout,rPhoneNumberAbout;
    Button rbtnLogout;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        type = getIntent().getStringExtra("type");

        rFullNameAbout = findViewById(R.id.FullNameAbout);
        rEmailAbout = findViewById(R.id.EmailAbout);
        rPhoneNumberAbout = findViewById(R.id.PhoneNumberAbout);
        rbtnLogout = findViewById(R.id.btnLogout);

        rFullNameAbout.setEnabled(false);
        rEmailAbout.setEnabled(false);
        rPhoneNumberAbout.setEnabled(false);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("user");


        table_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user user = dataSnapshot.child(userSharedPreference.getUserName(about.this)).getValue(user.class);
                assert user != null;
                rFullNameAbout.setText(user.getFullName());
                rEmailAbout.setText(user.getEmail());
                rPhoneNumberAbout.setText(user.getPhoneNumber());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        rbtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userSharedPreference.setUserName(about.this,"");
                if(type.equals("Login")) {
                    Intent login = new Intent(about.this, login.class);
                    startActivity(login);
                }
                else if(type.equals("Register"))
                {
                    Intent register = new Intent(about.this, register.class);
                    startActivity(register);
                }
            }
        });


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
                Intent login=new Intent(about.this,login.class);
                startActivity(login);
                return true;

            case R.id.home_item:
                Intent home = new Intent(about.this,MapsActivity.class);
                startActivity(home);
                return true;

            case R.id.prev_orders:
                Intent prev = new Intent(about.this,prevOrder.class);
                startActivity(prev);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
