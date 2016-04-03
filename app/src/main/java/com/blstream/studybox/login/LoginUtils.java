package com.blstream.studybox.login;

import android.content.Context;
import android.content.SharedPreferences;

import com.blstream.studybox.Constants;
import com.blstream.studybox.model.AuthCredentials;

public class LoginUtils {

    private static final String LOGIN_PREF_FILE = "com.blstream.studybox.LoginPreference";
    private static final String LOGIN_STATUS = "LoginStatus";
    private static final String LOGIN_EMAIL = "LoginEmail";
    private static final String LOGIN_PASSWORD = "LoginPassword";

    public static void saveUser(AuthCredentials credentials, Context context) {
        SharedPreferences prefs = context.getSharedPreferences(LOGIN_PREF_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(LOGIN_EMAIL, credentials.getEmail());
        editor.putString(LOGIN_PASSWORD, credentials.getPassword());
        editor.putBoolean(LOGIN_STATUS, true);
        editor.apply();
    }

    public static void deleteUser(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(LOGIN_PREF_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(LOGIN_EMAIL);
        editor.remove(LOGIN_PASSWORD);
        editor.putBoolean(LOGIN_STATUS, false);
        editor.apply();
    }

    public static boolean isUserLoggedIn(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(LOGIN_PREF_FILE, Context.MODE_PRIVATE);
        return prefs.getBoolean(LOGIN_STATUS, false);
    }
}
