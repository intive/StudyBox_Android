package com.blstream.studybox;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeSplashScreen(SPLASH_DISPLAY_LENGTH, DecksActivity.class);
    }

    /**
     * Use this method to start a new Activity and close SplashScreen after set miliseconds
     * @param delay amount of miliseconds to wait before finishing this activity
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
