package com.blstream.studybox.api;

import com.blstream.studybox.model.AuthCredentials;
import com.blstream.studybox.model.database.DecksList;

import retrofit.Callback;
import retrofit.RequestInterceptor;

/**
 * Created by Bartosz Kozajda on 09.03.2016.
 */
public class RestClientManager {

    public static void getAllDecks(String json, Callback<DecksList> callback) {
        RestInterface restInterface = new RestClient().getService();
        restInterface.getAllDecks(json, callback);
    }

    public static void authenticate(RequestInterceptor interceptor, Callback<AuthCredentials> callback) {
        RestInterface restInterface = new RestClient(interceptor).getService();
        restInterface.authenticate(callback);
    }

    public static void signUp(AuthCredentials credentials, Callback<AuthCredentials> callback) {
        RestInterface restInterface = new RestClient().getService();
        restInterface.signUp(credentials, callback);
    }
}
