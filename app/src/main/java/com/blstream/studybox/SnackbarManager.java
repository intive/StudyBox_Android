package com.blstream.studybox;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * If device loses network connection for unspecified reason user will be warned in form of snackbar
 * showing him proper message and enable him/her to perform action ("DISMISS" for now - when this
 * action is perform snackbar won't show again, unless device connect and disconnect again).
 * If network connection return by the time snackbar is shown, it (snackbar) will dismiss itself.
 */

public class SnackbarManager {
    private Snackbar snackbar;
    private static boolean isSnackbarDismissed = false;

    public SnackbarManager(Context context) {
        snackbar = createSnackbar(context);
    }

    private Snackbar createSnackbar(Context context) {

        View rootView = ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content);
        View view = useCoordinatorLayoutIfPossible(rootView);

        return Snackbar
                .make(view, "No internet connection.", Snackbar.LENGTH_INDEFINITE)
                .setAction("DISMISS", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isSnackbarDismissed = true;
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
        dismissIfSnackbarIsShownOrQueued(snackbar);
        isSnackbarDismissed = false;
    }

    private void dismissIfSnackbarIsShownOrQueued(Snackbar snackbar) {
        if (snackbar.isShownOrQueued()) {
            snackbar.dismiss();
        }
    }

    public void networkUnavailable() {
        if (!isSnackbarDismissed && !(snackbar.isShown()))
            snackbar.show();
    }
}
