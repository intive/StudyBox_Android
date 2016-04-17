package com.blstream.studybox.model;

import com.google.gson.annotations.Expose;

public class AuthCredentials {

    @Expose
    private String id;

    @Expose
    private String name;

    @Expose
    private String email;

    private String password;

    public AuthCredentials(String email, String password)  {
        this.email = email;
        this.password = password;
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

    public void setPassword(String password) {
        this.password = password;
    }
}
