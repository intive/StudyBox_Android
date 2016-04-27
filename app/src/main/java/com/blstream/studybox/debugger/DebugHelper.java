package com.blstream.studybox.debugger;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.os.StrictMode.VmPolicy;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.GINGERBREAD;


/**
 * Class used to initialize debug helpers for developer
 * Consists of LeakCanary, StrictMode and customized Logger
 */
public class DebugHelper {

    private static final String TAG = "STUDYBOXDEV";

    private static final boolean ENABLE_STRICT_MODE        = true;
    private static final boolean ENABLE_LEAK_CANARY        = true;
    private static final boolean STRICT_MODE_KILL_ON_ERROR = false;
    private static final int     METHOD_COUNT              = 3;
    private static final int     METHOD_OFFSET             = 2;

    private static boolean isDebuggable;
    private static boolean loggerInitialized;
    private static boolean strictModeInitialized;
    private static boolean leakCanaryInitialized;

    /**
     * Activates DebugHelper
     * Provides control over StrictMode and LeakCanary initialization.
     * @param application the application which will be debug enhanced
     */
    public static void initialize(Application application) {
        checkIfIsDebuggable(application.getApplicationInfo());

        initializeLogger();
        if (isDebuggable()) {
            if (ENABLE_STRICT_MODE) {
                initializeStrictMode();
            }
            if (ENABLE_LEAK_CANARY) {
                initializeLeakCanary(application);
            }
        }
    }


    // =============== Public LOGGER methods =====
    /**
     * Log simple string message
     * @param message your message to be logged
     */
    public static void logString(String message) {
        logString(message, null);
    }
    /**
     * Log simple string message
     * @param message your message to be logged
     * @param userTag additional tag that should be used for logging
     */
    public static void logString(String message, String userTag) {
        loggerString(message, userTag);
    }
    /**
     * Log and format JSON String
     * @param json your json in String to be logged
     */
    public static void logJson(String json) {
        logJson(json, null);
    }
    /**
     * Log and format JSON String
     * @param json your json in String to be logged
     * @param userTag additional tag that should be used for logging
     */
    public static void logJson(String json, String userTag) {
        loggerJson(json, userTag);
    }
    /**
     * Log exception
     * @param exception your exception to be logged
     * @param message your additional message to be logged
     */
    public static void logException(Exception exception, String message) {
        logException(exception, message, null);
    }
    /**
     * Log exception
     * @param exception your exception to be logged
     * @param message your additional message to be logged
     * @param userTag additional tag that should be used for logging
     */
    public static void logException(Exception exception, String message, String userTag) {
        loggerException(exception, message, userTag);
    }


    // =============== Public methods =====
    /** Check whether or not current build is in debug mode
     * @return boolean true if build is in debug, false if release
     */
    public static boolean isDebuggable() {
        return isDebuggable;
    }
    /**
     * Check whether or not Logger was initialized successfully
     * @return boolean true if Logger was initialized, false if error
     */
    public static boolean isLoggerInitialized() {
        return loggerInitialized;
    }
    /**
     * Check whether or not StrictMode was initialized
     * @return boolean true if StrictMode was initialized, false if not
     */
    public static boolean isStrictModeInitialized() {
        return strictModeInitialized;
    }
    /**
     * Check whether or not LeakCanary was initialized
     * @return boolean true if LeakCanary was initialized, false if not
     */
    public static boolean isLeakCanaryInitialized() {
        return leakCanaryInitialized;
    }


    // =============== Initialization methods =====
    private static void checkIfIsDebuggable(ApplicationInfo applicationInfo) {
        if(applicationInfo != null) {
            isDebuggable = (applicationInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        }
    }
    private static void initializeLogger() {
        if (!loggerInitialized) {
            LogLevel level = isDebuggable() ? LogLevel.FULL : LogLevel.NONE;

            Logger.init(TAG)                        // default PRETTYLOGGER or use just init()
                    .logLevel(level)                // default LogLevel.FULL
                    .methodCount(METHOD_COUNT)      // default 2
                    .methodOffset(METHOD_OFFSET);   // default 0

            loggerInitialized = true;
        }
    }
    private static void initializeStrictMode() {
        if (!strictModeInitialized) {
            if (SDK_INT >= GINGERBREAD) {
                ThreadPolicy.Builder threadPolicy = new ThreadPolicy.Builder();
                VmPolicy.Builder     vmPolicy     = new VmPolicy.Builder();

                threadPolicy.detectAll()
                            .penaltyLog();
                vmPolicy.detectAll()
                            .penaltyLog();

                if (STRICT_MODE_KILL_ON_ERROR) {
                    threadPolicy.penaltyDeath();
                    vmPolicy.penaltyDeath();
                }

                StrictMode.setThreadPolicy(threadPolicy.build());
                StrictMode.setVmPolicy(vmPolicy.build());

                strictModeInitialized = true;
            }
        }
    }
    private static void initializeLeakCanary(Application application) {
        if (!leakCanaryInitialized) {
            LeakCanary.install(application);
            leakCanaryInitialized = true;
        }
    }


    // =============== Helper methods =====
    private static void loggerString(String message, String userTag) {
        Logger.t(userTag).d(message);
    }
    private static void loggerJson(String json, String userTag) {
        Logger.t(userTag).json(json);
    }
    private static void loggerException(Exception exception, String message, String userTag) {
        Logger.t(userTag).e(exception, message);
    }

}
