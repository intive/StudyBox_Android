package com.blstream.studybox.api;

import android.content.Context;

import com.blstream.studybox.model.DecksList;

import retrofit.Callback;

/**
 * Created by Bartosz Kozajda on 09.03.2016.
 */
public class RestClientManager {
    public static RestClient client = new RestClient();

    public static RestInterface getRestApi(){
        return client.getService();
    }
    public static void getAllDecks(String json, Callback<DecksList> callback){
        RestInterface restInterface = getRestApi();
        restInterface.getAllDecks(json, callback);
    }
}
