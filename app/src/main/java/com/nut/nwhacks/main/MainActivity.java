package com.nut.nwhacks.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.db.chart.animation.Animation;
import com.db.chart.model.LineSet;
import com.db.chart.view.LineChartView;
import com.nut.nwhacks.R;
import com.nut.nwhacks.logtrip.TagListAdapter;
import com.nut.nwhacks.settings.AddTagActivity;
import com.nut.nwhacks.triplist.TripListActivity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    /**
     * Figure out what we're doing in here
     */

    private static final String TAG = MainActivity.class.getSimpleName();

    private static List<String> sTagList;
    private static List<Integer> sScoreList;
    private static TagListAdapter sAdapter;
    private int mNumTags;
    private LineChartView mLineChartView;
    private ListView mListView;
    private String[] mLabels;
    private Float[] mValues;
    private LineSet mDataSet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTagsFromPreferences();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeViews();
        generateScores();
        prepareScoresForChart();
        drawChart();

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
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?>adapter, View v, int position, long id){
                Intent intent = new Intent(context, TripListActivity.class);
                intent.putExtra("TAG", sTagList.get(position));
                startActivity(intent);
            }
        });
    }

    private void initializeViews() {
        mLineChartView = (LineChartView) findViewById(R.id.lineChart);
        mListView = (ListView)findViewById(R.id.main_listview);
        sAdapter = new TagListAdapter(this, R.layout.itemlistrow, sTagList);
        mListView.setAdapter(sAdapter);
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

    public void drawChart() {
        Animation anim = new Animation(1000);
        mLineChartView.addData(mDataSet);
        mLineChartView.show(anim);
    }

    private void generateScores() {
        sScoreList = new ArrayList<>();
        for (int i = 0; i < sTagList.size(); i++) {
            sScoreList.add(new Random().nextInt(10));
        }
    }

    public static int getScore(int index) {
        return sScoreList.get(index);
    }

    public void prepareScoresForChart() {
        List<String> labels = new ArrayList<>();
        for (int i = 0; i < sTagList.size(); i++) {
            labels.add(String.valueOf(i+1));
        }
        mLabels = new String[labels.size()];
        labels.toArray(mLabels);

        List<Float> floatScores = new ArrayList<>();
        for (int i = 0; i < sScoreList.size(); i++) {
            floatScores.add(sScoreList.get(i) * 1.0f);
        }
        mValues = new Float[floatScores.size()];
        floatScores.toArray(mValues);
        addEntries();
    }

    private void addEntries() {
        mDataSet = new LineSet();
        mDataSet.setColor(Color.parseColor(getResources().getString(0+R.color.colorAccent)))
                .setFill(Color.parseColor(getResources().getString(0+R.color.colorPrimaryDark)))
                .setSmooth(true);

        if (mLabels.length == mValues.length) {
            for (int i = 0; i < mValues.length; i++) {
                mDataSet.addPoint(mLabels[i], mValues[i]);
            }
        } else {
            System.out.println("the labels and values aren't the same length");
        }
    }
}
