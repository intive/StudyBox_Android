package com.blstream.studybox.api;

import com.blstream.studybox.model.AuthCredentials;
import com.blstream.studybox.model.database.Card;
import com.blstream.studybox.model.database.Decks;
import com.blstream.studybox.model.database.DecksList;

import java.util.List;

import retrofit.Callback;
import retrofit.RequestInterceptor;

/**
 * Created by Bartosz Kozajda on 09.03.2016.
 */
public class RestClientManager {

    public static void getDecks(String key, RequestInterceptor interceptor, RequestCallback<List<Decks>> callback) {
        RestInterface restInterface = new RestClient(interceptor).getService();
        restInterface.getDecks(key, callback);
    }

    public static void getFlashcards(String key, RequestInterceptor interceptor, RequestCallback<List<Card>> callback) {
        RestInterface restInterface = new RestClient(interceptor).getService();
        restInterface.getFlashcards(key, callback);
    }

    public static void authenticate(RequestInterceptor interceptor, Callback<AuthCredentials> callback) {
        RestInterface restInterface = new RestClient(interceptor).getService();
        restInterface.authenticate(callback);
    }
}
