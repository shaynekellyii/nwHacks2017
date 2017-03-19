package com.nut.nwhacks.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.nut.nwhacks.R;
import com.nut.nwhacks.settings.AddTagActivity;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /**
     * Figure out what we're doing in here
     */

    private static final String TAG = MainActivity.class.getSimpleName();

    private ListView mListView;

    private List<String> mTagList;
    private TagListAdapter mAdapter;
    private int mNumTags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTagsFromPreferences();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mListView = (ListView)findViewById(R.id.main_listview);
        mAdapter = new TagListAdapter(this, R.layout.itemlistrow, mTagList);
        mListView.setAdapter(mAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                startNewTagActivity();
            }
        });
    }

    private void getTagsFromPreferences() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        mNumTags = settings.getInt("numTags", 0);
        mTagList = new LinkedList<>();
        for (int i = 0; i < mNumTags; i++) {
            mTagList.add(settings.getString("tag" + String.valueOf(i), "Default Tag"));
        }
    }

    private void startNewTagActivity() {
        startActivity(new Intent(this, AddTagActivity.class));
    }

    public List<String> getTagList() {
        return mTagList;
    }
}
