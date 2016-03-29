package com.blstream.studybox;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Class for monitoring network connection status.
 * If device loses network connection for unspecified reason user will be warned in form of snackbar
 * showing him proper message and enable him/her to perform action ("DISMISS" for now - when this
 * action is perform snackbar won't show again, unless device connect and disconnect again).
 * If network connection return by the time snackbar is shown, it (snackbar) will dismiss itself.
 */

public class ConnectionStatusMonitor extends BroadcastReceiver {
    private Snackbar snackbar = null;
    private Context contextFromActiveActivity = null;
    private static boolean isSnackbarDismissed = false;

    @Override
    public void onReceive(Context context, Intent intent) {
        // First time we use receiver. We need to assign context and create snackbar.
        if (contextFromActiveActivity == null) {
            contextFromActiveActivity = context;
            snackbar = createSnackbar(contextFromActiveActivity);
            // Context is different from previous, so activity changed and we need update context and snackbar.
        } else if (context != contextFromActiveActivity) {
            contextFromActiveActivity = context;
            snackbar = createSnackbar(contextFromActiveActivity);
        }

        checkIfConnected(contextFromActiveActivity, snackbar);
    }

    private Snackbar createSnackbar(Context context) {
        // Getting root view from activity that broadcast intent.
        View rootView = ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content);
        //CoordinatorLayout view = (CoordinatorLayout) rootView.findViewById(R.id.coordinatorLayout);

        View view = useCoordinatorLayoutIfPossible(rootView);

        Snackbar createdSnackbar = Snackbar
                .make(view, "No internet connection.", Snackbar.LENGTH_INDEFINITE)
                .setAction("DISMISS", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isSnackbarDismissed = true;
                    }
                });

        return createdSnackbar;
    }

    private View useCoordinatorLayoutIfPossible(View rootView) {
        View view;

        if (rootView.findViewById(R.id.coordinatorLayout) != null) {
            view = rootView.findViewById(R.id.coordinatorLayout);
        } else {
            view = rootView;
        }

        return view;
    }

    private void checkIfConnected(Context context, Snackbar snackbar) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork != null && activeNetwork.isConnected()) {
            dismissIfSnackbarIsShownOrQueued(snackbar);
            isSnackbarDismissed = false;
        } else if (!isSnackbarDismissed) {
            snackbar.show();
        }
    }

    private void dismissIfSnackbarIsShownOrQueued(Snackbar snackbar) {
        if (snackbar.isShownOrQueued()) {
            snackbar.dismiss();
        }
    }
}
