package com.blstream.studybox.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.blstream.studybox.ConnectionStatusReceiver;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public abstract class BaseAppCompatActivity extends AppCompatActivity {

    protected ConnectionStatusReceiver connectionStatusReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connectionStatusReceiver = new ConnectionStatusReceiver();
    }

    @Override
    protected void onResume(){
        super.onResume();
        registerReceiver(connectionStatusReceiver, ConnectionStatusReceiver.filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(connectionStatusReceiver);
    }

    /**
     * Applies custom font to every activity that overrides this method
     */
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
