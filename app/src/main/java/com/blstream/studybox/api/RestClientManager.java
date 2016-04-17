package com.blstream.studybox.api;

import com.blstream.studybox.model.AuthCredentials;
import com.blstream.studybox.model.database.Card;
import com.blstream.studybox.model.database.Decks;

import java.util.List;

import retrofit.Callback;
import retrofit.RequestInterceptor;

public class RestClientManager {

    public static void getDecks(String key, RequestInterceptor interceptor, RequestCallback<List<Decks>> callback) {
        RestInterface restInterface = new RestClient(interceptor).getService();
        restInterface.getDecks(key, callback);
    }

    public static void getPublicDecks(String key, RequestCallback<List<Decks>> callback){
        RestInterface restInterface = new RestClient().getService();
        restInterface.getDecks(key, callback);
    }

    public static void getFlashcards(String key, RequestCallback<List<Card>> callback) {
        RestInterface restInterface = new RestClient().getService();
        restInterface.getFlashcards(key, callback);
    }

    public static void authenticate(RequestInterceptor interceptor, Callback<AuthCredentials> callback) {
        RestInterface restInterface = new RestClient(interceptor).getService();
        restInterface.authenticate(callback);
    }
}
