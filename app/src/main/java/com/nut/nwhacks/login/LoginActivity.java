package com.nut.nwhacks.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.nut.nwhacks.R;
import com.nut.nwhacks.main.MainActivity;

import io.moj.java.sdk.MojioClient;
import io.moj.java.sdk.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    // Security lol
    private static final String APP_ID = "5acebfc1-8f79-4753-860e-f761328f0a44";
    private static final String SECRET_KEY = "93e7cc78-910b-4c5b-972a-7cac74d5f324";
    private MojioClient mMojioClient;

    private ConstraintLayout mConstraintLayout;
    private TextView mUsernameTextView;
    private TextView mPasswordTextView;
    private Button mLoginButton;

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
                performLoginCall();
            }
        });
    }

    private void applyViews() {
        mConstraintLayout = (ConstraintLayout)findViewById(R.id.login_constraintlayout);
        mUsernameTextView = (TextView)findViewById(R.id.username_edittext);
        mPasswordTextView = (TextView)findViewById(R.id.password_edittext);
        mLoginButton = (Button) findViewById(R.id.login_button);
    }

    private void performLoginCall() {
        Call<User> loginCall = mMojioClient.login(
                mUsernameTextView.getText().toString(), mPasswordTextView.getText().toString());
        loginCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    // Success! Log the user in!
                    Snackbar.make(mConstraintLayout, "Login successful @jefking", Snackbar.LENGTH_LONG).show();
                    goToMainActivity();
                } else {
                    // Handle the error - this means we got a response without a success code. The user probably
                    // entered the wrong username or password
                    Snackbar.make(mConstraintLayout, "Login failed. smfh", Snackbar.LENGTH_LONG).show();
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
}
