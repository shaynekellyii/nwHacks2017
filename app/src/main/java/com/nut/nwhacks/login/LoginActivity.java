package com.nut.nwhacks.login;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nut.nwhacks.R;
import com.nut.nwhacks.logtrip.LogTripActivity;
import com.nut.nwhacks.main.MainActivity;
import com.nut.nwhacks.triplist.TripListActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.moj.java.sdk.MojioClient;
import io.moj.java.sdk.logging.Log;
import io.moj.java.sdk.model.Trip;
import io.moj.java.sdk.model.User;
import io.moj.java.sdk.model.Vehicle;
import io.moj.java.sdk.model.VehicleMeasure;
import io.moj.java.sdk.model.push.Observer;
import io.moj.java.sdk.model.response.ListResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    // Security lol
    private static final String APP_ID = "cd8d6364-cff4-4d28-94ea-6ab4b12261d6";
    private static final String SECRET_KEY = "7f04c40f-e825-45b7-b171-0a1fa0cf7754";
    private static final String BYPASS_USER = "skellyii@sfu.ca"; // DEBUG
    private static final String BYPASS_PASSWORD = "123456"; // DEBUG
    private static MojioClient mMojioClient;
    private ConstraintLayout mConstraintLayout;
    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private Button mLoginButton;
    private Button mLoginBypassButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        applyViews();

        mMojioClient = new MojioClient.Builder(APP_ID, SECRET_KEY).build();

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager keyboard = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                keyboard.hideSoftInputFromWindow(getWindow().getAttributes().token, 0);
                performLoginCall(false);
            }
        });
        mLoginBypassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performLoginCall(true);
            }
        });
    }

    private void applyViews() {
        mConstraintLayout = (ConstraintLayout)findViewById(R.id.login_constraintlayout);
        mUsernameEditText = (EditText)findViewById(R.id.username_edittext);
        mPasswordEditText = (EditText)findViewById(R.id.password_edittext);
        mLoginButton = (Button)findViewById(R.id.login_button);
        mLoginBypassButton = (Button)findViewById(R.id.login_bypass_button);
    }

    private void performLoginCall(boolean bypass) {
        Call<User> loginCall;
        if (bypass) {
            loginCall = mMojioClient.login(BYPASS_USER, BYPASS_PASSWORD);
        } else {
            loginCall = mMojioClient.login(
                    mUsernameEditText.getText().toString(), mPasswordEditText.getText().toString());
        }
        loginCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    // Success! Log the user in!
                    Snackbar.make(mConstraintLayout, "Login successful.", Snackbar.LENGTH_LONG).show();
                    goToMainActivity();
                } else {
                    // Handle the error - this means we got a response without a success code. The user probably
                    // entered the wrong username or password
                    Snackbar.make(mConstraintLayout, "Login failed. smh", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Snackbar.make(mConstraintLayout, "Login failed due to connectivity issue", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
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

    public static MojioClient getMojioClient() {
        return mMojioClient;
    }
}
