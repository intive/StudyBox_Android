package com.blstream.studybox.debugger;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.blstream.studybox.R;


// TODO: delete for final build
public class DebugHelperUsageExample extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debug_usage_example);


        // Filter Logcat for "STUDYBOXDEV" to see logger
        // Filter Logcat for "StrictMode" to see problems
        // LeakCanary works automatically, wait for notification


        // =============== How to log String =====
        DebugHelper.logString("foobar");
        DebugHelper.logString("foobar", "MyOwnTag");
        // =============== How to log String =====


        // =============== How to log JSON =====
        String json = "{\"employees\":[{\"firstName\":\"Foo\",\"lastName\":\"Bar\"}]}";
        DebugHelper.logJson(json);
        DebugHelper.logJson(json, "MyOwnTag");
        // =============== How to log JSON =====


        // =============== How to log Exception =====
        try {
            throw new Exception("Foo");
        } catch (Exception exception) {
            DebugHelper.logException(exception, "message");
            DebugHelper.logException(exception, "message", "MyOwnTag");
        }
        // =============== How to log Exception =====


        // =============== How to use helper methods =====
        boolean isDebugBuild = DebugHelper.isDebuggable();
        boolean isLoggerOn   = DebugHelper.isLoggerInitialized();
        boolean isSMOn       = DebugHelper.isStrictModeInitialized();
        boolean isLCOn       = DebugHelper.isLeakCanaryInitialized();
        // =============== How to use helper methods =====


    }


    public void startAsyncTask(View v) {
        // This async task is an anonymous class and therefore has a hidden reference to the outer
        // class MainActivity. If the activity gets destroyed before the task finishes (e.g. rotation),
        // the activity instance will leak.
        new AsyncTask<Void, Void, Void>() {
            @Override protected Void doInBackground(Void... params) {
                // Do some slow work in background
                SystemClock.sleep(20000);
                return null;
            }
        }.execute();
    }
}

