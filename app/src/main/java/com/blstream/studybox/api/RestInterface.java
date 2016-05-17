package com.blstream.studybox.api;

import com.blstream.studybox.model.AuthCredentials;
import com.blstream.studybox.model.database.Card;
import com.blstream.studybox.model.database.Deck;
import com.blstream.studybox.model.database.Tip;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

public interface RestInterface {

    @GET("/decks/")
    void getDecks(@Query("flashcardsCount") boolean flashcardCountKey, Callback<List<Deck>> cb);

    @GET("/decks/me")
    void getPrivateDecks(@Query("flashcardsCount") boolean flashcardCountKey, Callback<List<Deck>> cb);

    @GET("/decks/random/")
    void getRandomDeck(@Query("flashcardsCount") boolean flashcardCountKey, Callback<Deck> cb);

    @GET("/decks/{key}/flashcards")
    void getFlashcards(@Path("key") String key, @Query("random") String amount, Callback<List<Card>> cb);

    @GET("/decks/{keyDeck}/flashcards/{keyFlashcard}/tips")
    void getTips(@Path("keyDeck") String keyDeck, @Path("keyFlashcard") String keyFlashcard, Callback<List<Tip>> cb);

    @GET("/users/me")
    void authenticate(Callback<AuthCredentials> callback);

    @POST("/users/")
    void signUp(@Body AuthCredentials authCredentials, Callback<AuthCredentials> callback);

    @GET("/decks")
    void getDecksByName(@Query("name") String deckName,
                        @Query("flashcardsCount") boolean flashcardCountKey,
                        Callback<List<Deck>> cb);

    @GET("/decks")
    void getDecksByNameLoggedin(@Query("name") String deckName,
                                @Query("flashcardsCount") boolean flashcardCountKey,
                                @Query("isPublic") boolean isPublic,
                                @Query("includeOwn") boolean includeOwn,
                                Callback<List<Deck>> cb);
}
