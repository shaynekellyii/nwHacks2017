package com.nut.nwhacks.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nut.nwhacks.R;
import com.nut.nwhacks.logtrip.TagListAdapter;
import com.nut.nwhacks.settings.AddTagActivity;
import com.nut.nwhacks.triplist.TripListActivity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /**
     * Figure out what we're doing in here
     */

    private static final String TAG = MainActivity.class.getSimpleName();

    private static List<String> sTagList;
    private static TagListAdapter sAdapter;
    private int mNumTags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTagsFromPreferences();

        setContentView(R.layout.activity_main);
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
