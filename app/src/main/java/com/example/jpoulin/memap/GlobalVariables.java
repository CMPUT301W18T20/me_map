package com.example.jpoulin.memap;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

/**
 * Created by jpoulin on 2018-03-11.
 */

public class GlobalVariables extends Application {
    private String location = new String();

    public void setLocation (String passedLocation) {
        this.location = passedLocation;
    }

    public String getLocation () {
        return this.location;
    }
}
