package com.blstream.studybox.api;

import android.app.Application;

import com.blstream.studybox.api.DecksManager;
import com.blstream.studybox.event.BusProvider;
import com.squareup.otto.Bus;

/**
 * Created by Bartosz Kozajda on 08.03.2016.
 */
public class DecksApplication extends Application {
    private DecksManager decksManager;
    private Bus bus = BusProvider.getInstance();

    @Override
    public void onCreate() {
        decksManager = new DecksManager(this, bus);
        bus.register(decksManager);
        bus.register(this);
    }
}
