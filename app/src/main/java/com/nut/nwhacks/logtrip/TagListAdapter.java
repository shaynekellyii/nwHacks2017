package com.nut.nwhacks.logtrip;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nut.nwhacks.R;
import com.nut.nwhacks.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by skelly on 3/18/17.
 */

public class TagListAdapter extends ArrayAdapter<String> {

    private static List<Integer> sScoreList = new ArrayList<>();

    public TagListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public TagListAdapter(Context context, int resource, List<String> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.itemlistrow, null);
        }

        String tag = getItem(position);

        if (tag != null) {
            TextView tagView = (TextView)v.findViewById(R.id.taglist_textview);
            if (tagView != null) {
                tagView.setText(tag);
            }
            TextView scoreView = (TextView)v.findViewById(R.id.scorelist_textview);
            if (scoreView != null) {
                scoreView.setText(String.valueOf(MainActivity.getScore(position)));
            }
        }

        return v;
    }

    public static List<Integer> getScoreList() {
        return sScoreList != null ? sScoreList : new ArrayList<Integer>();
    }
}