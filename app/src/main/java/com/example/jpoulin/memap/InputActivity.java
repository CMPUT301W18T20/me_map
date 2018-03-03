package com.example.jpoulin.memap;

import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.app.Application;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by jpoulin on 2018-03-03.
 */

public class InputActivity extends AppCompatActivity {

    public EditText location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_activity);

        Button send = findViewById(R.id.button);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
                Log.e("Button did click", "yes");
                location = findViewById(R.id.location);
                String location_string = location.getText().toString();
                Log.e("Location string", location_string);
                Location location = new Location(location_string);
//                Address address = new Address(location);
                Intent intent = new Intent(InputActivity.this, MapsActivity.class);
                intent.putExtra("location", location_string);
                startActivity(intent);

            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

}