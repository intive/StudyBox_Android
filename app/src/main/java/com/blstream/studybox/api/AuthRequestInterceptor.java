package com.blstream.studybox.api;

import android.util.Base64;

import retrofit.RequestInterceptor;

public class AuthRequestInterceptor implements RequestInterceptor {

    private String basicCredentials;

    public AuthRequestInterceptor(String email, String password) {
        this.basicCredentials =
                "Basic " + Base64.encodeToString((email + ":" + password).getBytes(), Base64.NO_WRAP);
    }

    @Override
    public void intercept(RequestFacade request) {
        request.addHeader("Authorization", basicCredentials);
    }
}
