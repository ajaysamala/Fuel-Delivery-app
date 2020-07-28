package com.example.fueldelivery1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.fueldelivery1.model.stations;
import com.example.fueldelivery1.utils.userSharedPreference;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    public boolean homePageDisplay = true;

    private GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;
    LatLng currentLocation;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 10, locationListener);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //Log.i("Location", location.toString());
                //LatLng yourLocation = new LatLng(location.getLatitude(),location.getLongitude());
                //mMap.clear();
                //mMap.addMarker(new MarkerOptions().position(yourLocation).title("Marker at Current Location"));
                //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(yourLocation,17F));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // ask for permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        else {
            // we have permission!
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 10, locationListener);
            Location lastKnownLocation = (Location) locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            LatLng lastKnownCoordinates = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
            //mMap.clear();
            handleNewLocation(lastKnownLocation,mMap);
            currentLocation = lastKnownCoordinates;
            //mMap.addMarker(new MarkerOptions().position(lastKnownCoordinates).title("Marker at Current Location"));
            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastKnownCoordinates, 17F));
        }
    }


    public int getDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        Double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        /*double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
              + " Meter   " + meterInDec);
        return Radius * c;*/
        return kmInDec;
    }



    public boolean CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        /*double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
              + " Meter   " + meterInDec);
        return Radius * c;*/
        if(kmInDec<6)
        {
            return true;
        }
        else
        {
            return false;
        }

    }



    private void handleNewLocation(Location location,GoogleMap mMap) {
        Log.d("USER REQUEST :", location.toString());
        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();
        LatLng latLng = new LatLng(currentLatitude, currentLongitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        mMap.setMyLocationEnabled(true);
    }




    public void orderButtonClicked(final GoogleMap mMap)
    {
        mMap.clear();


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // ask for permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        else {
            // we have permission!
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 10, locationListener);
            Location lastKnownLocation = (Location) locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            LatLng lastKnownCoordinates = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
            //mMap.clear();
            currentLocation = lastKnownCoordinates;
            handleNewLocation(lastKnownLocation,mMap);
            //mMap.addMarker(new MarkerOptions().position(lastKnownCoordinates).title("Marker at Current Location"));
            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastKnownCoordinates, 13F));
        }



        if(userSharedPreference.getUserName(MapsActivity.this).length() != 0) {
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference dbref = firebaseDatabase.getReference();
            dbref.child("stations").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot f : dataSnapshot.getChildren()) {
                        stations st = f.getValue(stations.class);
                        double latitude = st.getLatitude();
                        double longitude = st.getLongitude();

                        final LatLng storedLocation = new LatLng(latitude, longitude);
                        if(CalculationByDistance(currentLocation,storedLocation) == true) {
                            mMap.addMarker(new MarkerOptions().position(storedLocation).title(st.getName())).setTag(st.getId());
                            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(storedLocation, 17F));
                        }

                        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                            @Override
                            public void onInfoWindowClick(Marker marker) {
                                if(marker.getPosition().latitude != currentLocation.latitude & marker.getPosition().longitude!=currentLocation.longitude) {
                                    //Log.i("PRINT ON USER REQUEST",""+ marker.getTitle());
                                    //Log.i("PRINT ON USER REQUEST",""+ marker.getPosition());
                                    //Log.i("PRINT ON USER REQUEST",""+ currentLocation);

                                    Intent intent1 = new Intent(MapsActivity.this, order.class);
                                    Integer id = (Integer) marker.getTag();
                                    intent1.putExtra("stationID", id);
                                    //Log.i("PRINT ON USER REQUEST",""+ id.toString());
                                    Integer distance = getDistance(currentLocation, marker.getPosition());
                                    //Log.i("PRINT ON USER REQUEST",""+ distance.toString());
                                    intent1.putExtra("distance", distance);
                                    startActivity(intent1);
                                }
                                else
                                {
                                    Toast.makeText(MapsActivity.this, "That's Your Location", Toast.LENGTH_LONG).show();
                                }
                            }
                        });



                    }
                    Toast.makeText(MapsActivity.this, "Please select a station from those displayed on the MAP!", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else
        {
            Toast.makeText(MapsActivity.this, "Please login before you Order!", Toast.LENGTH_LONG).show();
        }
    }





    public void showAllStationsButtonClicked(final GoogleMap mMap)
    {
        mMap.clear();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference dbref = firebaseDatabase.getReference();
        dbref.child("stations").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot f : dataSnapshot.getChildren()) {
                    stations st = f.getValue(stations.class);
                    LatLng storedLocation = new LatLng(st.getLatitude(), st.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(storedLocation).title(st.getName())).setTag(st.getId());
                }
                Toast.makeText(MapsActivity.this, "All the Stations are showed on the MAP!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mMap.setOnInfoWindowClickListener(null);
    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_axtivity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.login_item:
                Intent login=new Intent(MapsActivity.this,login.class);
                startActivity(login);
                return true;

            case R.id.home_item:
                Intent home = new Intent(MapsActivity.this,MapsActivity.class);
                startActivity(home);
                return true;

            case R.id.order_item:
                //Intent order = new Intent(MapsActivity.this,order.class);
                //startActivity(order);
                orderButtonClicked(mMap);
                return true;

            case R.id.prev_orders:
                Intent prev = new Intent(MapsActivity.this,prevOrder.class);
                startActivity(prev);

            case R.id.showAllStations:
                showAllStationsButtonClicked(mMap);

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
