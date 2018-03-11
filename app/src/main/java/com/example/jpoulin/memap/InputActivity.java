package com.example.jpoulin.memap;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


/**
 * Created by jpoulin on 2018-03-03.
 */

public class InputActivity extends FragmentActivity {

    public EditText location;
    MapView mMapView;
    public String location_string;
    public Locale locale;
    public Geocoder geocoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_activity);

        locale = new Locale("en", "CA");

        geocoder = new Geocoder(this, locale);

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
                Intent intent = new Intent(InputActivity.this, MapsActivity.class);
                intent.putExtra("location", location_string);
                startActivity(intent);

            }
        });

    }

    public void setGlobalLocation(String location) {
        ((GlobalVariables) this.getApplication()).setLocation(location);
    }



    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}