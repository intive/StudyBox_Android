package com.blstream.studybox;

import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    private ConnectionStatusMonitor connectionStatusMonitor = new ConnectionStatusMonitor();

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(Constants.ACTION);
        registerReceiver(connectionStatusMonitor, filter);
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(connectionStatusMonitor);
    }
}
