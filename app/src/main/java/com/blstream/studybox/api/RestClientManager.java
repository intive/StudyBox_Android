package com.blstream.studybox.api;

import android.content.Context;

import com.blstream.studybox.model.DecksList;

import retrofit.Callback;

/**
 * Created by Bartosz Kozajda on 09.03.2016.
 */
public class RestClientManager {
    public static RestClient client = new RestClient();

    public static RestInterface getRestApi(Context context){
        return client.getService();
    }
    public static void getAllDecks(String json, Context context, Callback<DecksList> callback){
        RestInterface restInterface = getRestApi(context);
        restInterface.getAllDecks(json, callback);
    }
}
