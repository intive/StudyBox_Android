package com.blstream.studybox;

import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    public ConnectionStatusReceiver connectionStatusReceiver = new ConnectionStatusReceiver();

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(Constants.ACTION);
        registerReceiver(connectionStatusReceiver, filter);
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(connectionStatusReceiver);
    }
}
