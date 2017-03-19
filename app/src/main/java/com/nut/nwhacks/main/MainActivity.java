package com.nut.nwhacks.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import io.moj.java.sdk.MojioClient;
import io.moj.java.sdk.model.Trip;
import io.moj.java.sdk.model.Vehicle;
import io.moj.java.sdk.model.VehicleMeasure;
import io.moj.java.sdk.model.response.ListResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;
import java.util.Arrays;

import com.nut.nwhacks.R;
import com.nut.nwhacks.login.LoginActivity;
import com.nut.nwhacks.logtrip.TagListAdapter;
import com.nut.nwhacks.settings.AddTagActivity;
import com.nut.nwhacks.triplist.TripListActivity;

import java.util.ArrayList;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    /**
     * Figure out what we're doing in here
     */

    private static final String TAG = MainActivity.class.getSimpleName();
    private static List<String> sTagList;
    private static TagListAdapter sAdapter;
    private int mNumTags;
    private MojioClient mMojioClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTagsFromPreferences();

        setContentView(R.layout.activity_main);

        mMojioClient = LoginActivity.getmMojioClient();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView listView = (ListView)findViewById(R.id.main_listview);
        sAdapter = new TagListAdapter(this, R.layout.itemlistrow, sTagList);
        listView.setAdapter(sAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                startNewTagActivity();
            }
        });

        final Context context = this;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?>adapter, View v, int position, long id){
                Intent intent = new Intent(context, TripListActivity.class);
                intent.putExtra("TAG", sTagList.get(position));
                startActivity(intent);
            }
        });
/*
        Vehicle vehicle = new Vehicle();
        vehicle.setName("nut");
        vehicle.setVIN("12345678901234567");
        vehicle.setLicensePlate("123456");
        createVehicle(vehicle);
        getVehicles();
        */

        getTripHistory("df33d676-49e8-4473-aae2-ac4b1ea85f41");
    }

    private void createVehicle(Vehicle vehicle) {
        mMojioClient.rest().createVehicle(vehicle).enqueue(new Callback<Vehicle>() {
            @Override
            public void onResponse(Call<Vehicle> call, Response<Vehicle> response) {
                if (response.isSuccessful()) {
                    Vehicle v = response.body();
                    Log.d("nut",v.toString());
                } else {
                    // Handle the error - this means we got a response without a success code. Are you logged in?
                }
            }

            @Override
            public void onFailure(Call<Vehicle> call, Throwable t) {
                // Handle the error - this is caused by a request failure such as loss of network connectivity
            }
        });
    }

    private void getTrips() {
        mMojioClient.rest().getTrips().enqueue(new Callback<ListResponse<Trip>>() {
            @Override
            public void onResponse(Call<ListResponse<Trip>> call, Response<ListResponse<Trip>> response) {
                if (response.isSuccessful()) {
                    List<Trip> trips = response.body().getData();
                    Log.d("nut",Arrays.toString(trips.toArray()));
                } else {
                    // Handle the error - this means we got a response without a success code. Are you logged in?
                }
            }

            @Override
            public void onFailure(Call<ListResponse<Trip>> call, Throwable t) {
                // Handle the error - this is caused by a request failure such as loss of network connectivity
            }
        });
    }

    private void getTripHistory(String id) {
        mMojioClient.rest().getTripStates(id).enqueue(new Callback<ListResponse<VehicleMeasure>>() {
            @Override
            public void onResponse(Call<ListResponse<VehicleMeasure>> call, Response<ListResponse<VehicleMeasure>> response) {
                if (response.isSuccessful()) {
                    List<VehicleMeasure> trips = response.body().getData();
                    Log.d("nut",Arrays.toString(trips.toArray()));
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

    private void getVehicles() {
        mMojioClient.rest().getVehicles().enqueue(new Callback<ListResponse<Vehicle>>() {
            @Override
            public void onResponse(Call<ListResponse<Vehicle>> call, Response<ListResponse<Vehicle>> response) {
                if (response.isSuccessful()) {
                    List<Vehicle> vehicles = response.body().getData();
                    Log.d("nut",Arrays.toString(vehicles.toArray()));
                } else {
                    // Handle the error - this means we got a response without a success code. Are you logged in?
                }
        }

            @Override
            public void onFailure(Call<ListResponse<Vehicle>> call, Throwable t) {
                // Handle the error - this is caused by a request failure such as loss of network connectivity
            }
        });
    }

    private void getTagsFromPreferences() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        mNumTags = settings.getInt("numTags", 0);
        sTagList = new LinkedList<>();
        for (int i = 0; i < mNumTags; i++) {
            sTagList.add(settings.getString("tag" + String.valueOf(i), "Default Tag"));
        }
    }

    private void startNewTagActivity() {
        startActivity(new Intent(this, AddTagActivity.class));
        this.finish();
    }

    public static List<String> getTagList() {
        return sTagList != null ? sTagList : new ArrayList<String>();
    }

    public static void refreshTagList() {
        sAdapter.notifyDataSetChanged();
    }

}
