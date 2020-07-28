package com.example.fueldelivery1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.fueldelivery1.model.orders;
import com.example.fueldelivery1.utils.userSharedPreference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class prevOrder extends AppCompatActivity {

    ListView orderType,orderedTime,orderedPrice;
            //,orderedFrom;
    String phnNo;
    //String FromName="Hello";
    orders or;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prev_order);

        orderType = findViewById(R.id.orderTypeXML);
        //orderedFrom = findViewById(R.id.orderedFromXML);
        orderedTime = findViewById(R.id.orderedTimeXML);
        orderedPrice = findViewById(R.id.orderedPriceXML);

        final ArrayList<String> orderTypeList = new ArrayList<String>();
        //final ArrayList<String> orderedFromList = new ArrayList<String>();
        final ArrayList<String> orderedTimeList = new ArrayList<String>();
        final ArrayList<String> orderedPriceList = new ArrayList<String>();

        final ArrayAdapter<String> orderTypeAdapter = new ArrayAdapter<String>(prevOrder.this,
                android.R.layout.simple_list_item_1, orderTypeList);
        //final ArrayAdapter<String> orderedFromAdapter = new ArrayAdapter<String>(prevOrder.this,
          //      android.R.layout.simple_list_item_1, orderedFromList);
        final ArrayAdapter<String> orderedTimeAdapter = new ArrayAdapter<String>(prevOrder.this,
                android.R.layout.simple_list_item_1, orderedTimeList);
        final ArrayAdapter<String> orderedPriceAdapter = new ArrayAdapter<String>(prevOrder.this,
                android.R.layout.simple_list_item_1, orderedPriceList);

        orderType.setAdapter(orderTypeAdapter);
        //orderedFrom.setAdapter(orderedFromAdapter);
        orderedTime.setAdapter(orderedTimeAdapter);
        orderedPrice.setAdapter(orderedPriceAdapter);

        phnNo = userSharedPreference.getUserName(prevOrder.this);


        orderTypeList.add("FuelType");
        orderTypeAdapter.notifyDataSetChanged();
        orderedTimeList.add("OrderTime");
        orderedTimeAdapter.notifyDataSetChanged();
        orderedPriceList.add("AmountPaid");
        orderedPriceAdapter.notifyDataSetChanged();

        FirebaseDatabase firebaseDatabase =FirebaseDatabase.getInstance();
        final DatabaseReference dbref = firebaseDatabase.getReference();
        final DatabaseReference dbref2 = firebaseDatabase.getReference();
        dbref.child("orders").child(phnNo).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot f : dataSnapshot.getChildren()) {
                    or = f.getValue(orders.class);
                    if (or!=null) {

                        /*Log.i("HELLO 1: ", or.getId().toString());
                        dbref2.child("stations").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                for (DataSnapshot r : dataSnapshot.getChildren()) {
                                    stations st = r.getValue(stations.class);

                                    //Log.i("HELLO 2: ", or.getId().toString());
                                    Log.i("HELLO 3: ", st.getId().toString());
                                    if (or.getId() == st.getId()) {
                                        FromName = st.getName();
                                        Log.i("HELLO 2: ", or.getId().toString());
                                        Log.i("HELLO 4: ", st.getId().toString()+"   "+FromName);
                                        orderedFromList.add(FromName);
                                        orderedFromAdapter.notifyDataSetChanged();
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });*/

                        //orderedFromList.add(FromName);
                        //orderedFromAdapter.notifyDataSetChanged();
                        orderTypeList.add(or.getOrderType());
                        orderTypeAdapter.notifyDataSetChanged();
                        orderedTimeList.add(or.getOrderTime());
                        orderedTimeAdapter.notifyDataSetChanged();
                        orderedPriceList.add(or.getPrice().toString());
                        orderedPriceAdapter.notifyDataSetChanged();
                    }
                }
                Toast.makeText(prevOrder.this, "All your orders are shown!", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
                Intent login=new Intent(prevOrder.this,login.class);
                startActivity(login);
                return true;

            case R.id.home_item:
                Intent home = new Intent(prevOrder.this,MapsActivity.class);
                startActivity(home);
                return true;

            case R.id.prev_orders:
                Intent prev = new Intent(prevOrder.this,prevOrder.class);
                startActivity(prev);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
