package com.blstream.studybox;

import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    private final String ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    private ConnectionStatusMonitor connectionStatusMonitor = new ConnectionStatusMonitor();

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(ACTION);
        registerReceiver(connectionStatusMonitor, filter);
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(connectionStatusMonitor);
    }
}
