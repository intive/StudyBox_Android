package com.blstream.studybox.model;

import com.google.gson.annotations.Expose;

public class AuthCredentials {

    @Expose(serialize = false)
    private String id;

    @Expose
    private String name;

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

    public AuthCredentials(String id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
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
