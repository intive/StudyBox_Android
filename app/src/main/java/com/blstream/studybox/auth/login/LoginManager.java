package com.blstream.studybox.auth.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import com.blstream.studybox.R;
import com.blstream.studybox.model.AuthCredentials;

public class LoginManager implements LoginInterface {

    private static final String LOGIN_PREF_FILE = "com.blstream.studybox.LoginPreference";
    private static final String LOGIN_STATUS = "LoginStatus";
    private static final String USER_EMAIL = "UserEmail";
    private static final String USER_PASSWORD = "UserPassword";
    private static final String USER_ID = "UserId";
    private static final String DEFAULT_EMAIL = "";
    private static final String DEFAULT_PASSWORD = "";
    private static final String DEFAULT_ID = "";

    private SharedPreferences preferences;
    private Resources resources;

    public LoginManager(Context context) {
        preferences = context.getSharedPreferences(LOGIN_PREF_FILE, Context.MODE_PRIVATE);
        resources = context.getResources();
    }

    @Override
    public void saveUser(AuthCredentials credentials) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USER_EMAIL, credentials.getEmail());
        editor.putString(USER_PASSWORD, credentials.getPassword());
        editor.putString(USER_ID, credentials.getId());
        editor.putBoolean(LOGIN_STATUS, true);
        editor.apply();
    }

    @Override
    public void deleteUser() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.putBoolean(LOGIN_STATUS, false);
        editor.apply();
    }

    @Override
    public boolean isUserLoggedIn() {
        return preferences.getBoolean(LOGIN_STATUS, false);
    }

    @Override
    public String getUserEmail() {
        if (isUserLoggedIn()) {
            return preferences.getString(USER_EMAIL, DEFAULT_EMAIL);
        } else {
            return resources.getString(R.string.default_username);
        }
    }

    @Override
    public String getUserPassword() {
        return preferences.getString(USER_PASSWORD, DEFAULT_PASSWORD);
    }

    @Override
    public String getUserId() {
        return preferences.getString(USER_ID, DEFAULT_ID);
    }

    @Override
    public AuthCredentials getCredentials() {
        return new AuthCredentials(getUserId(), getUserEmail(), getUserPassword());
    }
}
