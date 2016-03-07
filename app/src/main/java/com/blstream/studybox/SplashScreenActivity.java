package com.blstream.studybox;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreenActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeSplashScreen(SPLASH_DISPLAY_LENGTH, MainActivity.class);
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
}
