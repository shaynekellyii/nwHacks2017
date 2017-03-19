package com.nut.nwhacks.logtrip;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.nut.nwhacks.BaseActivity;
import com.nut.nwhacks.R;
import com.nut.nwhacks.main.MainActivity;

import java.util.List;

import io.moj.java.sdk.model.Trip;
import io.moj.java.sdk.model.response.ListResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Activity to ask if the user wants to log their trip
 */

public class LogTripActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {

    private Button mLogTrip;
    private Spinner mTagSpinner;
    private TextView mDoNotLogTrip;
    private List<String> mTagArray;
    private String mSelectedTag;
    private List<Trip> mAllTrips;
    private Trip mMostRecentTrip;
    private ConstraintLayout mConstraintLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_trip);
        getAllTrips();
        grabTagArray();
        initializeViews();

        mTagSpinner.setOnItemSelectedListener(this);

        mLogTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTagToTrip();
            }
        });

        mDoNotLogTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToMainActivity();
            }
        });
    }

    private void initializeViews() {
        mLogTrip = (Button) findViewById(R.id.log_trip_button);
        mTagSpinner = (Spinner) findViewById(R.id.tags);
        mDoNotLogTrip = (TextView) findViewById(R.id.do_not_log_trip);
        mConstraintLayout = (ConstraintLayout) findViewById(R.id.log_trip_constraintlayout);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, mTagArray);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mTagSpinner
                .setAdapter(adapter);
    }

    private void grabTagArray() {
        mTagArray = MainActivity.getTagList();
    }

    private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        mSelectedTag = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // Auto-generated method stub
    }

    public void addTagToTrip() {
        mMojioClient.rest().addTag("Trips", mMostRecentTrip.getId(), mSelectedTag).enqueue(new Callback<String[]>() {
            @Override
            public void onResponse(Call<String[]> call, Response<String[]> response) {
                if (response.isSuccessful()) {
                    Snackbar.make(mConstraintLayout, "Successfully tagged trip", Snackbar.LENGTH_LONG).show();
                    goToMainActivity();
                } else {
                    Snackbar.make(mConstraintLayout, "Couldn't tag. smh", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String[]> call, Throwable t) {

            }
        });
    }

    public void getAllTrips() {
        mMojioClient.rest().getTrips().enqueue(new Callback<ListResponse<Trip>>() {
            @Override
            public void onResponse(Call<ListResponse<Trip>> call, Response<ListResponse<Trip>> response) {
                if (response.isSuccessful()) {
                    mAllTrips = response.body().getData();
                    mMostRecentTrip = mAllTrips.get(0);
                } else {
                    // Handle the error - this means we got a response without a success code. Are you logged in?
                }
            }

            @Override
            public void onFailure(Call<ListResponse<Trip>> call, Throwable t) {

            }
        });
    }
}
