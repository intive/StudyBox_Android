package com.blstream.studybox;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreenActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 3000;
    private boolean isSplashActive;
    private final String BUNDLE_KEY = "SPLASH_DISPLAYED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            isSplashActive = savedInstanceState.getBoolean(BUNDLE_KEY);
        }

        if (!isSplashActive) {
            initializeSplashScreen(SPLASH_DISPLAY_LENGTH, MainActivity.class);
            isSplashActive = true;
        }
    }

    /**
     * Use this method to start a new Activity and close SplashScreen after set milliseconds
     * @param delay amount of milliseconds to wait before finishing this activity
     * @param destination Activity that should be started after the SplashScreen
     */
    private void initializeSplashScreen(int delay, final Class<?> destination) {
        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        Intent mainIntent = new Intent(SplashScreenActivity.this, destination);
                        SplashScreenActivity.this.startActivity(mainIntent);
                        SplashScreenActivity.this.finish();
                    }
                }, delay);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(BUNDLE_KEY, isSplashActive);
        super.onSaveInstanceState(savedInstanceState);
    }
}
