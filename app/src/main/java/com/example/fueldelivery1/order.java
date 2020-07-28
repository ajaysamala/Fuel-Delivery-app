package com.example.fueldelivery1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fueldelivery1.model.costs;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class order extends AppCompatActivity {

    public String type="";
    public Integer Quantity=1;
    public Integer totalcost,fuelcost,deliverycost,id,distance;
    public TextView rfuelcost,rbasefare,rdelivery,rtotalAmount;
    public Button rbtnPayment,rbtnIncrement,rbtnDecrement;
    RadioGroup radioGroup;

    public void calculation()
    {
        if(type == "")
        {
            Toast.makeText(order.this, "Please Choose the Type of fuel first!", Toast.LENGTH_LONG).show();
        }
        else {
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference dbRef = firebaseDatabase.getReference();

            dbRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    costs cost = dataSnapshot.child("costs").getValue(costs.class);

                    if (type == "Petrol") {
                        fuelcost = Quantity * cost.costPetrol;
                    } else if (type == "Deisel") {
                        fuelcost = Quantity * cost.costDeisel;
                    } else if (type == "CNG") {
                        fuelcost = Quantity * cost.costCNG;
                    }
                    deliverycost = cost.costPerKM * distance;


                    totalcost = fuelcost + deliverycost + cost.costBasefare;

                    rfuelcost.setText(fuelcost.toString());
                    rbasefare.setText(cost.costBasefare.toString());
                    rdelivery.setText(deliverycost.toString());
                    rtotalAmount.setText(totalcost.toString());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);


        //This is used to store the costs data for once!
        /*FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = firebaseDatabase.getReference();
        costs cost1 = new costs(15,40,65,5,75);
        dbRef.child("costs").setValue(cost1);*/


        id=getIntent().getIntExtra("stationID",0);
        distance=getIntent().getIntExtra("distance",0);

        Log.i("PRINT ON USER REQUEST:o",""+ distance.toString());
        Log.i("PRINT ON USER REQUEST:o",""+ id.toString());

        radioGroup = findViewById(R.id.typeRadioGroup);
        rfuelcost = findViewById(R.id.fuelcost);
        rbasefare = findViewById(R.id.basefare);
        rdelivery = findViewById(R.id.delivery);
        rtotalAmount = findViewById(R.id.totalCost);
        rbtnDecrement = findViewById(R.id.btnDecrement);
        rbtnIncrement = findViewById(R.id.btnIncrement);
        rbtnPayment = findViewById(R.id.btnPayment);
        rbtnIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView quant = findViewById(R.id.quantity);
                Integer quantity = Integer.parseInt(quant.getText().toString());
                quantity = quantity+1;
                Quantity=quantity;
                quant.setText(Quantity.toString());
                calculation();
            }
        });

        rbtnDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView quant = findViewById(R.id.quantity);
                Integer quantity = Integer.parseInt(quant.getText().toString());
                if(quantity == 1)
                {
                    Toast.makeText(order.this, "The minimum quantity is one!", Toast.LENGTH_LONG).show();
                }
                else {
                    quantity = quantity - 1;
                    Quantity = quantity;
                    quant.setText(Quantity.toString());
                    calculation();
                }
            }
        });

        rbtnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(type == "")
                {
                    Toast.makeText(order.this, "Choose the type of fuel!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Intent payment = new Intent(order.this, com.example.fueldelivery1.payment.class);
                    payment.putExtra("totalAmount",totalcost);
                    payment.putExtra("stationID",id);
                    payment.putExtra("FuelType",type);

                    //Log.i("PRINT ON USERREQUEST:o2",""+ distance+"  "+id+"  "+type+"  "+totalcost);

                    startActivity(payment);
                }
            }
        });
    }



    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.radioPetrol:
                if (checked){
                    type = "Petrol";
                    calculation();
                    break;
                }
            case R.id.radioDeisel:
                if (checked) {
                    type = "Deisel";
                    calculation();
                    break;
                }
            case R.id.radioCNG:
                if (checked) {
                    type = "CNG";
                    calculation();
                    break;
                }
        }
    }
}
