package com.example.fueldelivery1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class homelogo extends AppCompatActivity {

    Button rletsGetStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homelogo);

        rletsGetStarted = findViewById(R.id.letsGetStarted);

        rletsGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(homelogo.this,MapsActivity.class);
                startActivity(home);
            }
        });

    }
}
