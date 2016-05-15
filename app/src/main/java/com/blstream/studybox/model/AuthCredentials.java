package com.blstream.studybox.model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;

public class AuthCredentials {

    @Expose(serialize = false)
    private String id;

    @Expose
    private String email;

    @Expose(deserialize = false)
    private String password;

    private String repeatPassword;

    public AuthCredentials(String email, String password)  {
        this.email = email;
        this.password = password;
    }

    public AuthCredentials(String email, String password, String repeatPassword)  {
        this.email = email;
        this.password = password;
        this.repeatPassword = repeatPassword;
    }

    public AuthCredentials(String id, String email, String password, @Nullable String name) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
