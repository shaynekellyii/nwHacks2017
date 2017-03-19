package com.nut.nwhacks;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.nut.nwhacks.logtrip.LogTripActivity;

import io.moj.java.sdk.MojioClient;

/**
 * Created by YingYing on 2017-03-19.
 */

public class BaseActivity extends AppCompatActivity {

    protected static final String APP_ID = "cd8d6364-cff4-4d28-94ea-6ab4b12261d6";
    protected static final String SECRET_KEY = "7f04c40f-e825-45b7-b171-0a1fa0cf7754";
    protected static final String BYPASS_USER = "swifties2017@gmail.com"; // DEBUG
    protected static final String BYPASS_PASSWORD = "test123!"; // DEBUG
    protected static MojioClient mMojioClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void createNotification(View view) {
        // Prepare intent which is triggered if the
        // notification is selected
        Intent intent = new Intent(this, LogTripActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

        // Build notification
        // Actions are just fake
        Notification noti = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("You've finished a trip!")
                .setContentText("Tag blah blah blah blah")
                .setContentIntent(pIntent)
                .addAction(R.mipmap.ic_launcher_round, "Action2", pIntent).build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // hide the notification after its selected
        noti.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(0, noti);
    }

}
