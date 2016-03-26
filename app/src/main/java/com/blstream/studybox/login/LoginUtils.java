package com.blstream.studybox.login;

import android.content.Context;
import android.content.SharedPreferences;

import com.blstream.studybox.Constants;

public class LoginUtils {
    public static void saveUser(String email, String password, Context context) {
        SharedPreferences prefs = context.getSharedPreferences(Constants.LOGIN_PREF_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Constants.LOGIN_EMAIL, email);
        editor.putString(Constants.LOGIN_PASSWORD, password);
        editor.putBoolean(Constants.LOGIN_STATUS, true);
        editor.apply();
    }

    public static void deleteUser(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(Constants.LOGIN_PREF_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(Constants.LOGIN_EMAIL);
        editor.remove(Constants.LOGIN_PASSWORD);
        editor.putBoolean(Constants.LOGIN_STATUS, false);
        editor.apply();
    }

    public static boolean isUserLoggedIn(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(Constants.LOGIN_PREF_FILE, Context.MODE_PRIVATE);
        return prefs.getBoolean(Constants.LOGIN_STATUS, false);
    }
}
