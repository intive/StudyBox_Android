package com.blstream.studybox.base;

import android.content.Context;
import android.os.Bundle;

import com.blstream.studybox.ConnectionStatusReceiver;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.hannesdorfmann.mosby.mvp.viewstate.MvpViewStateActivity;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public abstract class BaseViewStateActivity<V extends MvpView, P extends MvpBasePresenter<V>>
        extends MvpViewStateActivity<V, P> {

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
