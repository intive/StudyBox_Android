package com.blstream.studybox.api;

import com.blstream.studybox.model.DecksList;

import org.json.JSONObject;

import retrofit.Callback;
import retrofit.RequestInterceptor;

public class RestClientManager {

    public static void getAllDecks(String json, String url, Callback<DecksList> callback){
        RestInterface restInterface = new RestClient(url).getService();
        restInterface.getAllDecks(json, callback);
    }

    public static void authenticate(String url, RequestInterceptor interceptor, Callback<JSONObject> callback) {
        RestInterface restInterface = new RestClient(url, interceptor).getService();
        restInterface.authenticate(callback);
    }
}
