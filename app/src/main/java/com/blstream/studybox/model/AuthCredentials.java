package com.blstream.studybox.model;

public class AuthCredentials {

    String email;
    String password;

    public AuthCredentials(String email, String password)  {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
