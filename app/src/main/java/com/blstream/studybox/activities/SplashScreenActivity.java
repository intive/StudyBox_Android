package com.blstream.studybox.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.blstream.studybox.login.LoginUtils;

public class SplashScreenActivity extends AppCompatActivity {

    private static final int SPLASH_DISPLAY_LENGTH = 3000;
    private static Handler   splashHandler;
    private static Runnable  splashRunnable;
    private static boolean   splashRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (splashHandler == null) {
            splashHandler = new Handler();
        }

        if (splashRunnable == null) {
            if (LoginUtils.isUserLoggedIn(SplashScreenActivity.this)){
                splashRunnable = initializeSplashRunnable(DecksActivity.class);
            } else {
                splashRunnable = initializeSplashRunnable(LoginActivity.class);
            }
        }

        if (!splashRunning) {
            initializeSplashScreen(SPLASH_DISPLAY_LENGTH);
        }
    }

    private Runnable initializeSplashRunnable(final Class<?> destination) {
        return new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashScreenActivity.this, destination);
                SplashScreenActivity.this.startActivity(mainIntent);
                SplashScreenActivity.this.finish();
            }
        };
    }

    private void initializeSplashScreen(int delay) {
        splashHandler.postDelayed(splashRunnable, delay);
        splashRunning = true;
    }


    @Override
    protected void onStop() {
        if (splashRunning) {
            splashHandler.removeCallbacks(splashRunnable);
        }
        splashHandler = null;
        splashRunnable = null;
        splashRunning = false;
        super.onStop();
    }
}
