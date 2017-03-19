package com.nut.nwhacks.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.nut.nwhacks.R;
import com.nut.nwhacks.settings.SettingsActivity;

public class MainActivity extends AppCompatActivity {

    /**
     * Figure out what we're doing in here
     */

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final float DEFAULT_LAT_LON = 0.0f;

    private float homeLat = DEFAULT_LAT_LON;
    private float homeLon = DEFAULT_LAT_LON;
    private float workLat = DEFAULT_LAT_LON;
    private float workLon = DEFAULT_LAT_LON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        restorePreferences();
    }

    private void restorePreferences() {
        SharedPreferences settings = getPreferences(MODE_PRIVATE);
        homeLat = settings.getFloat("homeLat", DEFAULT_LAT_LON);
        homeLon = settings.getFloat("homeLon", DEFAULT_LAT_LON);
        workLat = settings.getFloat("workLat", DEFAULT_LAT_LON);
        workLon = settings.getFloat("workLon", DEFAULT_LAT_LON);
        if (homeLat == DEFAULT_LAT_LON ||
                homeLon == DEFAULT_LAT_LON ||
                workLat == DEFAULT_LAT_LON ||
                workLon == DEFAULT_LAT_LON) {
            startActivity(new Intent(this, SettingsActivity.class));
        }
    }

}
