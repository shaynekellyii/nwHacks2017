package com.nut.nwhacks.triplist;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.nut.nwhacks.R;
import com.nut.nwhacks.logtrip.TagListAdapter;

import java.util.ArrayList;
import java.util.List;

public class TripListActivity extends AppCompatActivity {

    private static final String TAG = TripListActivity.class.getSimpleName();

    private static List<String> sTripList;
    private static TagListAdapter sAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_list);
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setVisibility(View.GONE);

        String tag = "Default tag";
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            Toast.makeText(this, "Invalid tag", Toast.LENGTH_LONG).show();
            this.finish();
        } else {
            tag = extras.getString("TAG");
        }

        sTripList = buildTripList(tag);

        ListView listView = (ListView)findViewById(R.id.main_listview);
        sAdapter = new TagListAdapter(this, R.layout.itemlistrow, sTripList);
        listView.setAdapter(sAdapter);
    }

    private List<String> buildTripList(String tag) {
        // TODO: Get list of trips with the tag param from the mojio api
        // For now use this mock data
        List<String> tripList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            tripList.add("Trip " + i);
        }
        return tripList;
    }

    public static List<String> getTripList() {
        return sTripList != null ? sTripList : new ArrayList<String>();
    }
}
