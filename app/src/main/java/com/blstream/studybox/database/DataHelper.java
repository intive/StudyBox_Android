package com.blstream.studybox.database;

import android.content.Context;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.blstream.studybox.Constants;
import com.blstream.studybox.api.AuthRequestInterceptor;
import com.blstream.studybox.api.RequestCallback;
import com.blstream.studybox.api.RequestListener;
import com.blstream.studybox.api.RestClientManager;
import com.blstream.studybox.login.LoginManager;
import com.blstream.studybox.model.AuthCredentials;
import com.blstream.studybox.model.database.Card;
import com.blstream.studybox.model.database.Deck;
import com.blstream.studybox.model.database.Decks;
import com.blstream.studybox.model.database.DecksList;

import java.util.List;

import retrofit.RetrofitError;

/**
 * Created by Łukasz on 2016-03-26.
 */
public class DataHelper implements DataProvider {
    private List<Card> downloadedCards;
    private static final String DECKS_KEY = "decks";

    @Override
    public List<Decks> getDecks() {
        return Decks.getAll();
    }
    public List<Card> getFlashcards() {
        return downloadedCards;
    }

    public void downloadFlashcard(String deckId, final RequestListener<String> listener, Context context) {
        LoginManager loginManager = new LoginManager(context);
        RestClientManager.getFlashcards(deckId, new AuthRequestInterceptor(loginManager.getCredentials()), new RequestCallback<>(new RequestListener<List<Card>>() {
            @Override
            public void onSuccess(List<Card> response) {
                downloadedCards = response;
                listener.onSuccess("Flashcards downloaded successfully");
            }

            @Override
            public void onFailure(RetrofitError error) {
                listener.onFailure(error);
            }
        }));
    }

    public void deleteAllDecks() {
        new Delete().from(Deck.class).execute();
    }

    public void downloadUserDecks(final RequestListener<String> listener, Context context) {
        LoginManager loginManager = new LoginManager(context);
        RestClientManager.getDecks(DECKS_KEY, new AuthRequestInterceptor(loginManager.getCredentials()), new RequestCallback<>(new RequestListener<List<Decks>>() {
            @Override
            public void onSuccess(List<Decks> response) {
                saveDecksToDataBase(response);
                listener.onSuccess("New decks or nothing new");
            }

            @Override
            public void onFailure(RetrofitError error) {
                listener.onFailure(error);
            }
        }));
    }

    private void saveDecksToDataBase(List<Decks> decks) {
        List<Decks> deckList = decks;
        for (Decks deck : deckList) {
            deck.save();
        }
    }
}
