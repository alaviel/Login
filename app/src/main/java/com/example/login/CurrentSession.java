package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class CurrentSession extends AppCompatActivity {
    private static final String TAG = "CURRENT";

    private TextView textViewTokenData;

    private final String key = "ejercicio1";
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_session);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            token = extras.getString("TOKEN");
        }

        init();
    }

    private void init() {
        Log.d(TAG, "init");
        TextView textViewToken = findViewById(R.id.textViewToken);
        textViewToken.setText(token);

        textViewTokenData = findViewById(R.id.textViewTokenData);
        decomposeToken();
    }

    private void decomposeToken() {
        Log.d(TAG, "decomposeToken");
        //TODO: Implement Key Verification
        String[] userData = new String[0];

        try {
        userData = JWTUtils.decoded(token);
        Log.d(TAG, "decomposeToken: userData local size: " + userData.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, "decomposeToken: userData size: " + userData.length);
        if(userData.length >= 3){
            String dataString = "Titular: " + userData[0] +
                    "\nURL: " + userData[1] +
                    "\nEmail: " + userData[2];
            textViewTokenData.setText(dataString);
        }
    }
}