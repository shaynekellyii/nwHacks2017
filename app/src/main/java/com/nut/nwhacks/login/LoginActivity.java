package com.nut.nwhacks.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.nut.nwhacks.BaseActivity;
import com.nut.nwhacks.R;
import com.nut.nwhacks.main.MainActivity;

import io.moj.java.sdk.MojioClient;
import io.moj.java.sdk.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    private ConstraintLayout mConstraintLayout;
    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private Button mLoginButton;
    private Button mLoginBypassButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMojioClient = new MojioClient.Builder(APP_ID, SECRET_KEY).build();
        setContentView(R.layout.activity_login);

        applyViews();

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
}
