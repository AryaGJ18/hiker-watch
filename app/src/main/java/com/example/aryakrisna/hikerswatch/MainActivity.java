package com.example.aryakrisna.hikerswatch;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    LocationManager locationManager;
    LocationListener locationListener;
    TextView textView;
    //String message ="";

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getSupportActionBar().hide();

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        textView = findViewById(R.id.textView2);
        locationListener = new LocationListener() {
            String message;
            @Override
            public void onLocationChanged(Location location) {
                message = "";
                message += "Latitude: " + location.getLatitude() + "\r\n\n";
                message += "Longitude: " + location.getLongitude() + "\r\n\n";
                message += "Accuracy: " + location.getAccuracy() + "\r\n\n";
                message += "Altitude: " + location.getAltitude() + "\r\n\n";
                message += "Address:" + "\r\n";

                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

                try{
                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                    if(addresses != null && addresses.size() > 0){

                        if(addresses.get(0).getThoroughfare() != null){
                            message += addresses.get(0).getThoroughfare() + "\r\n";
                        }

                        if(addresses.get(0).getLocality() != null){
                            message += addresses.get(0).getLocality() + "\r\n";
                        }

                        if(addresses.get(0).getPostalCode() != null){
                            message += addresses.get(0).getPostalCode() + " ";
                        }

                        if(addresses.get(0).getAdminArea() != null){
                            message += addresses.get(0).getAdminArea() + "";
                        }
                    }

                }catch (Exception e){

                }

                textView.setText(message);

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

        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }



    }


}
