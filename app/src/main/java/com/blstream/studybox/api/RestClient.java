package com.blstream.studybox.api;

import com.blstream.studybox.model.DecksList;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by Bartosz Kozajda on 08.03.2016.
 */
public class RestClient {
    private static final String BASE_URL = "http://private-5f2e4b-studybox2.apiary-mock.com/";
    private static final String API_KEY = "json";

    private static RestClient restClient;
    private static RestAdapter restAdapter;

    public static RestClient getClient(){
        if(restClient == null)
            restClient = new RestClient();
        return restClient;
    }

    public void getDecks(Callback<DecksList> callback){
        RestInterface restInterface = restAdapter.create(RestInterface.class);
        restInterface.getAllDecks(API_KEY,callback);
    }

    private RestClient(){
        restAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .build();
    }
}
