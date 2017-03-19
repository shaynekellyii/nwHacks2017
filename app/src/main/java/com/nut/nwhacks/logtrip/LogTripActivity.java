package com.nut.nwhacks.logtrip;

import android.content.Intent;
import android.os.Bundle;
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

/**
 * Activity to ask if the user wants to log their trip
 */

public class LogTripActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {

    private Button mLogTrip;
    private Spinner mTagSpinner;
    private TextView mDoNotLogTrip;
    private List<String> mTagArray;
    private String mSelectedTag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_trip);

        grabTagArray();
        initializeViews();

        mTagSpinner.setOnItemSelectedListener(this);

        mLogTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: ASSOCIATE TRIP WITH TAG USE mSELECTEDTAG
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
}
