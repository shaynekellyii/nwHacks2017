package com.nut.nwhacks.settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nut.nwhacks.R;
import com.nut.nwhacks.main.MainActivity;

public class AddTagActivity extends AppCompatActivity {

    private EditText mTagEt;
    private Button mSubmitBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tag);
        applyViews();

        mSubmitBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLocationSettings();
            }
        });
    }

    private void applyViews() {
        mTagEt = (EditText)findViewById(R.id.tag_edittext);
        mSubmitBt = (Button)findViewById(R.id.submit_tag_button);
    }

    private void setLocationSettings() {
        if (mTagEt.getText().toString().isEmpty()) {
            Snackbar.make(mTagEt, "Please enter a tag", Snackbar.LENGTH_LONG);
            return;
        }

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        int numTags = settings.getInt("numTags", 0);
        String tagName = "tag" + String.valueOf(numTags);

        SharedPreferences.Editor editor = settings.edit();
        editor.putString(tagName, mTagEt.getText().toString()).commit();
        editor.putInt("numTags", numTags + 1).commit();
        Toast.makeText(this.getApplicationContext(), "Tag successfully added", Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, MainActivity.class));
        this.finish();
    }
}
