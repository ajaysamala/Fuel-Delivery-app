package com.example.fueldelivery1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.fueldelivery1.model.orders;
import com.example.fueldelivery1.utils.userSharedPreference;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class ordered extends AppCompatActivity {

    public Integer stationID,totalcost;
    String FuelType;
    TextView rorderID;
    String storingorderID;
    Button rgotoHomePage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordered);

        rorderID = findViewById(R.id.orderID);
        rgotoHomePage = findViewById(R.id.gotoHomePage);

        stationID = getIntent().getIntExtra("stationID",0);
        totalcost = getIntent().getIntExtra("Totalcost",0);
        FuelType = getIntent().getStringExtra("FuelType");

        Log.i("USERREQUEST : ORDERED",""+"  "+stationID+"  "+FuelType+"  "+totalcost);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dbChildRef = database.getReference("orders");

        String currentTime = Calendar.getInstance().getTime().toString().substring(4,20);

        storingorderID = String.valueOf(System.currentTimeMillis());
        rorderID.setText("OrderID : "+storingorderID);

        Integer id = getIntent().getExtras().getInt("stationID");
        orders or = new orders(id,FuelType, currentTime,(float)totalcost);
        dbChildRef.child(userSharedPreference.getUserName(ordered.this)).child(storingorderID).setValue(or);

        rgotoHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(ordered.this,MapsActivity.class);
                startActivity(home);
            }
        });

    }
}
