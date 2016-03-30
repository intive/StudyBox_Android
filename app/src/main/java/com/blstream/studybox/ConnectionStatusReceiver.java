package com.blstream.studybox;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for monitoring network connection status.
 * In every activity should be:
 *  public ConnectionStatusReceiver connectionStatusReceiver = new ConnectionStatusReceiver();
 * and
 *  IntentFilter filter = new IntentFilter(Constants.ACTION);
 *  registerReceiver(connectionStatusReceiver, filter);
 * in OnResume() method, and
 *  unregisterReceiver(connectionStatusReceiver);
 * in OnPause() method.
 *
 * This receiver provides also interface "ConnectionStatusReceiverListener" with two methods :
 * - networkAvailable()
 * - networkUnavailable()
 * To use this you need to implement "ConnectionStatusReceiver.ConnectionStatusReceiverListener" in
 * your activity and call "connectionStatusReceiver.addListener(this);" in e.g. .
 */

public class ConnectionStatusReceiver extends BroadcastReceiver {
    private SnackbarManager snackbarManager = null;

    private ConnectivityManager connectivityManager;
    private List<ConnectionStatusReceiverListener> listeners;
    private boolean connected;

    public ConnectionStatusReceiver() {
        listeners = new ArrayList<>();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getExtras() == null)
            return;

        connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (checkStateChanged(connectivityManager))
            notifyStateToAll();

        notifySnackbarManager(context);
    }

    private boolean checkStateChanged(ConnectivityManager connectivityManager) {
        boolean prev = connected;
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        connected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return prev != connected;
    }

    private void notifyStateToAll() {
        for (ConnectionStatusReceiverListener listener : listeners)
            notifyState(listener);
    }

    private void notifyState(ConnectionStatusReceiverListener listener) {
        if (listener != null) {
            if (connected) {
                listener.networkAvailable();
            } else {
                listener.networkUnavailable();
            }
        }
    }

    private void notifySnackbarManager(Context context) {
        if (connected)
            notifySnackbarClassNetworkAvailable();
        else
            notifySnackbarClassNetworkUnavailable(context);
    }

    private void notifySnackbarClassNetworkAvailable() {
        if (snackbarManager != null) {
            snackbarManager.networkAvailable();
        }
    }

    private void notifySnackbarClassNetworkUnavailable(Context context) {
        if (snackbarManager == null) {
            snackbarManager = new SnackbarManager(context);
        }
        snackbarManager.networkUnavailable();
    }

    public void addListener(ConnectionStatusReceiverListener listener) {
        listeners.add(listener);
        notifyState(listener);
    }

    public void removeListener(ConnectionStatusReceiverListener listener) {
        listeners.remove(listener);
    }

    public interface ConnectionStatusReceiverListener {
        void networkAvailable();

        void networkUnavailable();
    }
}