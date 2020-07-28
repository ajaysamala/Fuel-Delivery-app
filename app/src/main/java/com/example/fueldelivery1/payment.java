package com.example.fueldelivery1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

public class payment extends AppCompatActivity {

    public Integer stationID,totalcost;
    String FuelType;
    String paymentType="";
    Button rPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        stationID = getIntent().getIntExtra("stationID",0);
        totalcost = getIntent().getIntExtra("totalAmount",0);
        FuelType = getIntent().getStringExtra("FuelType");

        //Log.i("PRINT ON USERREQUEST:p",""+"  "+stationID+"  "+FuelType+"  "+totalcost);

        rPay = findViewById(R.id.Pay);

        rPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(paymentType == ""){
                    Toast.makeText(payment.this, "Select Mode of Payment!", Toast.LENGTH_LONG).show();
                }
                else if(paymentType == "COD"){
                    Intent del = new Intent(payment.this,ordered.class);
                    del.putExtra("Totalcost",totalcost);
                    del.putExtra("stationID",stationID);
                    del.putExtra("FuelType",FuelType);
                    startActivity(del);
                }
                else if(paymentType == "Card"){
                    Intent card = new Intent(payment.this,cardPayment.class);
                    card.putExtra("Totalcost",totalcost);
                    card.putExtra("stationID",stationID);
                    card.putExtra("FuelType",FuelType);
                    startActivity(card);
                }
            }
        });

    }


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioCOD:
                if (checked) {
                    paymentType = "COD";
                    break;
                }
            case R.id.radioCARD:
                if (checked) {
                    paymentType = "Card";
                    break;
                }
        }
    }
}
