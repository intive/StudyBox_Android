package com.blstream.studybox;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * If device loses network connection for unspecified reason user will be warned in form of snackbar
 * showing him proper message and enable him/her to perform action (open android settings).
 * If network connection return by the time snackbar is shown, it (snackbar) will dismiss itself.
 */

public class SnackbarManager {
    private Snackbar snackbar;

    public SnackbarManager(Context context) {
        snackbar = createSnackbar(context);
    }

    private Snackbar createSnackbar(final Context context) {

        View rootView = ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content);
        View view = useCoordinatorLayoutIfPossible(rootView);

        return Snackbar
                .make(view, R.string.no_internet_connection, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.open_options, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((Activity) context).startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                    }
                });
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

    public void networkAvailable() {
        if (snackbar.isShownOrQueued()) {
            snackbar.dismiss();
        }
    }

    public void networkUnavailable() {
        if (!(snackbar.isShown()))
            snackbar.show();
    }
}
