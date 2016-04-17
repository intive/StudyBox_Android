package com.blstream.studybox.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;

import com.blstream.studybox.ConnectionStatusReceiver;
import com.blstream.studybox.auth.BaseAuthPresenter;
import com.blstream.studybox.auth.BaseAuthView;
import com.hannesdorfmann.mosby.mvp.viewstate.MvpViewStateActivity;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public abstract class BaseAuthActivity<V extends BaseAuthView, P extends BaseAuthPresenter<V>>
        extends MvpViewStateActivity<V, P> {

    protected final static float ENABLED_BUTTON_ALPHA = 1.0f;
    protected final static float DISABLED_BUTTON_ALPHA = 0.5f;

    protected ConnectionStatusReceiver connectionStatusReceiver;

    protected abstract void setError(String message);
    protected abstract void setFieldError(TextInputEditText field, String message);
    protected abstract void setFormEnabled(boolean enabled);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connectionStatusReceiver = new ConnectionStatusReceiver();
        setRetainInstance(true);
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
