package com.blstream.studybox.login;

import android.content.Context;
import android.content.SharedPreferences;

import com.blstream.studybox.model.AuthCredentials;

public class LoginManager implements LoginInterface {

    private static final String LOGIN_PREF_FILE = "com.blstream.studybox.LoginPreference";
    private static final String LOGIN_STATUS = "LoginStatus";
    private static final String LOGIN_EMAIL = "LoginEmail";
    private static final String LOGIN_PASSWORD = "LoginPassword";
    private static final String DEFAULT_EMAIL = "";
    private static final String DEFAULT_PASSWORD = "";

    private SharedPreferences preferences;

    public LoginManager(Context context) {
        preferences = context.getSharedPreferences(LOGIN_PREF_FILE, Context.MODE_PRIVATE);
    }

    @Override
    public void saveUser(AuthCredentials credentials) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(LOGIN_EMAIL, credentials.getEmail());
        editor.putString(LOGIN_PASSWORD, credentials.getPassword());
        editor.putBoolean(LOGIN_STATUS, true);
        editor.apply();
    }

    @Override
    public void deleteUser() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(LOGIN_EMAIL);
        editor.remove(LOGIN_PASSWORD);
        editor.putBoolean(LOGIN_STATUS, false);
        editor.apply();
    }

    @Override
    public boolean isUserLoggedIn() {
        return preferences.getBoolean(LOGIN_STATUS, false);
    }

    @Override
    public String getUserEmail() {
        return preferences.getString(LOGIN_EMAIL, DEFAULT_EMAIL);
    }

    @Override
    public String getUserPassword() {
        return preferences.getString(LOGIN_PASSWORD, DEFAULT_PASSWORD);
    }
}
