package com.nut.nwhacks.summary;

import com.nut.nwhacks.BaseActivity;
import com.nut.nwhacks.R;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.CameraUpdateFactory;

import android.os.Bundle;

/**
 * This shows how to create a simple activity with a map and a marker on the map.
 */
public class SummaryActivity extends BaseActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera.
     */
    @Override
    public void onMapReady(GoogleMap map) {
//        RelativeLayout rel = (RelativeLayout) findViewById(R.id.summary_layout);
//        Snackbar.make(rel, "Yo! Maps up.", Snackbar.LENGTH_LONG).show();
        // TODO: Get lat long from trip info
        try {
            LatLng pos = new LatLng(harshBrakeLocation.getLat(), harshBrakeLocation.getLng());

            map.addMarker(new MarkerOptions().position(pos).title("Harsh Break"));
            map.moveCamera(CameraUpdateFactory.newLatLng(pos));
        } catch(Exception e) {
            System.out.println("There was probably no harsh break.");
        }
    }
}