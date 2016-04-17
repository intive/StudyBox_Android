package com.blstream.studybox.api;

import com.blstream.studybox.model.AuthCredentials;
import com.blstream.studybox.model.database.Card;
import com.blstream.studybox.model.database.Decks;
import com.blstream.studybox.model.database.Tip;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface RestInterface {
    @GET("/{key}")
    void getDecks(@Path("key") String json, Callback<List<Decks>> cb);

    @GET("/decks/{key}/flashcards")
    void getFlashcards(@Path("key") String key, Callback<List<Card>> cb);

    @GET("/decks/{keyDeck}/flashcards/{keyFlashcard}/tips")
    void getTips(@Path("keyDeck") String keyDeck, @Path("keyFlashcard") String keyFlashcard, Callback<List<Tip>> cb);

    @GET("/users/me")
    void authenticate(Callback<AuthCredentials> callback);
}
