package com.blstream.studybox.login;

import android.content.Context;
import android.content.SharedPreferences;

import com.blstream.studybox.model.AuthCredentials;

public class LoginManager {

    private static final String LOGIN_PREF_FILE = "com.blstream.studybox.LoginPreference";
    private static final String LOGIN_STATUS = "LoginStatus";
    private static final String LOGIN_EMAIL = "LoginEmail";
    private static final String LOGIN_PASSWORD = "LoginPassword";
    private static final String DEFAULT_EMAIL = "";
    private static final String DEFAULT_PASSWORD = "";

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

    public static String getUserEmail(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(LOGIN_PREF_FILE, Context.MODE_PRIVATE);
        return prefs.getString(LOGIN_EMAIL, DEFAULT_EMAIL);
    }

    public static String getUserPassword(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(LOGIN_PREF_FILE, Context.MODE_PRIVATE);
        return prefs.getString(LOGIN_PASSWORD, DEFAULT_PASSWORD);
    }
}
