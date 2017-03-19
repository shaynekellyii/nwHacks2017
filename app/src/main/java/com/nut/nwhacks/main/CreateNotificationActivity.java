package com.nut.nwhacks.main;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.nut.nwhacks.R;
import com.nut.nwhacks.logtrip.LogTripActivity;

/**
 * Created by YingYing on 2017-03-18.
 */

public class CreateNotificationActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
//                    .addAction(R.mipmap.ic_launcher_round, "Call", pIntent)
//                    .addAction(R.mipmap.ic_launcher_round, "More", pIntent)
                    .addAction(R.mipmap.ic_launcher_round, "And more", pIntent).build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // hide the notification after its selected
        noti.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(0, noti);
    }
}
