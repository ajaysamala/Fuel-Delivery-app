package com.example.fueldelivery1;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.braintreepayments.cardform.view.CardForm;
public class cardPayment extends AppCompatActivity {
    public Integer stationID,totalcost;
    String FuelType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_payment);


        stationID = getIntent().getIntExtra("stationID",0);
        totalcost = getIntent().getIntExtra("Totalcost",0);
        FuelType = getIntent().getStringExtra("FuelType");

        final CardForm cardForm = findViewById(R.id.card_form);
        Button rbtnCompletePayment = findViewById(R.id.btnCompletePayment);
        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                //.postalCodeRequired(true)
                //.mobileNumberRequired(true)
                //.mobileNumberExplanation("SMS is required on this number")
                .setup(cardPayment.this);
        cardForm.getCvvEditText().setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);



        rbtnCompletePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cardForm.isValid()) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(cardPayment.this);
                    alertBuilder.setTitle("Confirm before purchase");
                    alertBuilder.setMessage("Card number: " + cardForm.getCardNumber() + "\n" +
                            "Card expiry date: " + cardForm.getExpirationDateEditText().getText().toString()
                            //+ "\n" + "Card CVV: " + cardForm.getCvv()
                            //+ "\n" +"Postal code: " + cardForm.getPostalCode() + "\n"
                            //+"Phone number: " + cardForm.getMobileNumber()
                            );
                    alertBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            //Toast.makeText(cardPayment.this, "Thank you for purchase", Toast.LENGTH_LONG).show();
                            Intent del = new Intent(cardPayment.this,ordered.class);
                            del.putExtra("Totalcost",totalcost);
                            del.putExtra("stationID",stationID);
                            del.putExtra("FuelType",FuelType);
                            startActivity(del);
                        }
                    });
                    alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog alertDialog = alertBuilder.create();
                    alertDialog.show();

                }else {
                    Toast.makeText(cardPayment.this, "Please fill all the details correctly!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
