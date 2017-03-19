package com.nut.nwhacks.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import io.moj.java.sdk.MojioClient;
import io.moj.java.sdk.model.VehicleMeasure;
import io.moj.java.sdk.model.response.ListResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;

import com.nut.nwhacks.R;
import com.nut.nwhacks.settings.SettingsActivity;

public class MainActivity extends AppCompatActivity {

    /**
     * Figure out what we're doing in here
     */

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final float DEFAULT_LAT_LON = 0.0f;
    private static final String APP_ID = "5acebfc1-8f79-4753-860e-f761328f0a44";
    private static final String SECRET_KEY = "93e7cc78-910b-4c5b-972a-7cac74d5f324";
    private MojioClient mMojioClient;

    private float homeLat = DEFAULT_LAT_LON;
    private float homeLon = DEFAULT_LAT_LON;
    private float workLat = DEFAULT_LAT_LON;
    private float workLon = DEFAULT_LAT_LON;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMojioClient = new MojioClient.Builder(APP_ID, SECRET_KEY).build();

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

    private void getTripHistoryResponses(String id, int top) {
        // mMojioClient.rest().getTripHistory(id, top).enqueue(new Callback<ListResponse<VehicleMeasure>>() {
        mMojioClient.rest().getTripStates(id, ).enqueue(new Callback<ListResponse<VehicleMeasure>>() {
            @Override
            public void onResponse(Call<ListResponse<VehicleMeasure>> call, Response<ListResponse<VehicleMeasure>> response) {
                if (response.isSuccessful()) {
                    List<VehicleMeasure> vehicles = response.body().getData();
                    // Show the user their vehicles!
                } else {
                    // Handle the error - this means we got a response without a success code. Are you logged in?
                }
            }

            @Override
            public void onFailure(Call<ListResponse<VehicleMeasure>> call, Throwable t) {
                // Handle the error - this is caused by a request failure such as loss of network connectivity
            }
        });
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
