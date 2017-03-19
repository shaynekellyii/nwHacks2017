package com.nut.nwhacks.triplist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.nut.nwhacks.R;
import com.nut.nwhacks.summary.SummaryActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TripListActivity extends AppCompatActivity {

    private static final String TAG = TripListActivity.class.getSimpleName();

    private static List<String> sTripList;
    private static List<Integer> sScoreList;
    private static TripListAdapter sAdapter;

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
        generateScores();

        ListView listView = (ListView)findViewById(R.id.main_listview);
        sAdapter = new TripListAdapter(this, R.layout.itemlistrow, sTripList);
        listView.setAdapter(sAdapter);

        final Context context = this;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?>adapter, View v, int position, long id){
                Intent intent = new Intent(context, SummaryActivity.class);
                startActivity(intent);
            }
        });
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

    private void generateScores() {
        sScoreList = new ArrayList<>();
        for (int i = 0; i < sTripList.size(); i++) {
            sScoreList.add(new Random().nextInt(10));
        }
    }

    public static List<String> getTripList() {
        return sTripList != null ? sTripList : new ArrayList<String>();
    }

    public static Integer getScore(int index) {
        return sScoreList.get(index);
    }
}
