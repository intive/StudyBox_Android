package com.blstream.studybox;

import android.content.Context;

import com.blstream.studybox.debugger.DebugHelper;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class Application extends com.activeandroid.app.Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        DebugHelper.initialize(this);
        customFontInit();
        context = this;
    }

    public static Context getContext() {
        return context;
    }

    /**
     * Use this method to define custom font that will be used as default application font
     */
    private void customFontInit() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Lato-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
