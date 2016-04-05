package com.blstream.studybox.api;

import com.blstream.studybox.Constants;
import com.blstream.studybox.model.AuthCredentials;
import com.blstream.studybox.model.database.DecksList;

import retrofit.Callback;
import retrofit.RequestInterceptor;

/**
 * Created by Bartosz Kozajda on 09.03.2016.
 */
public class RestClientManager {
    public static RestClient client = new RestClient(Constants.BASE_URL);

    public static RestInterface getRestApi() {
        return client.getService();
    }

    public static void getAllDecks(String json, Callback<DecksList> callback) {
        RestInterface restInterface = getRestApi();
        restInterface.getAllDecks(json, callback);
    }

    public static void authenticate(String url, RequestInterceptor interceptor, Callback<AuthCredentials> callback) {
        RestInterface restInterface = new RestClient(url, interceptor).getService();
        restInterface.authenticate(callback);
    }
}
