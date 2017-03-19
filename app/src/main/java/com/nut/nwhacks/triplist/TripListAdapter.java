package com.nut.nwhacks.triplist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nut.nwhacks.R;

import java.util.List;

/**
 * Created by skelly on 3/19/17.
 */

public class TripListAdapter extends ArrayAdapter<String> {

    public TripListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public TripListAdapter(Context context, int resource, List<String> items) {
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
                scoreView.setText(String.valueOf(TripListActivity.getScore(position)));
            }
        }

        return v;
    }
}