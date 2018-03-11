package com.example.jpoulin.memap;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String location_string;
    private EditText location;
    private Timer t = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_activity);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        try {
            MapsInitializer.initialize(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        Button send = findViewById(R.id.button);

        ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);
        exec.scheduleAtFixedRate(new Runnable() {
            public void run() {
                updateLocation();
            }
        }, 0, 1, TimeUnit.SECONDS); // execute every 60 seconds

    }


    public void updateLocation() {
        Button send = findViewById(R.id.button);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
                Log.e("Button did click", "yes");
                location = findViewById(R.id.location);
                location_string = location.getText().toString();
                setGlobalLocation(location_string);
                Log.e("Location string", location_string);
                Location location = new Location(location_string);

            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Bundle extras = getIntent().getExtras();

        String location = extras.getString("location");
        Log.e("Passed location", location);
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses = null;

        try {


            addresses = geocoder.getFromLocationName(location, 1);
            Log.e("Location", addresses.toString());
        }
        catch (IOException e){
            Log.e("Did it work?", "no, it did not");
        }

        assert addresses != null;
        Address current_address;
        try {
            current_address = addresses.get(0);

            Double lat = current_address.getLatitude();
            Double lon = current_address.getLongitude();


            // Add a marker in the user provided location and move the camera
            LatLng display_location = new LatLng(lat, lon);
            float zoomLevel = 12.0f;
            mMap.addMarker(new MarkerOptions().position(display_location).title("Marker at task location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(display_location, zoomLevel));

        } catch (IndexOutOfBoundsException e) {
            Toast.makeText(this, "Invalid location!", Toast.LENGTH_LONG).show();

        }

        updateLocation();

    }

    public void setGlobalLocation(String location) {
        ((GlobalVariables) this.getApplication()).setLocation(location);
    }

}
