package com.blstream.studybox.api;

import android.util.Base64;

import com.blstream.studybox.model.AuthCredentials;

import retrofit.RequestInterceptor;

public class AuthRequestInterceptor implements RequestInterceptor {

    private String basicCredentials;

    public AuthRequestInterceptor(AuthCredentials credentials) {
        String combinedCredentials = credentials.getEmail() + ":" + credentials.getPassword();
        this.basicCredentials = "Basic " + Base64.encodeToString(combinedCredentials.getBytes(), Base64.NO_WRAP);
    }

    @Override
    public void intercept(RequestFacade request) {
        request.addHeader("Authorization", basicCredentials);
    }
}
