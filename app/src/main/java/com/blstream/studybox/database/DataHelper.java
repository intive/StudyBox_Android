package com.blstream.studybox.database;

import android.content.Context;

import com.activeandroid.query.Delete;
import com.blstream.studybox.api.AuthRequestInterceptor;
import com.blstream.studybox.api.RequestCallback;
import com.blstream.studybox.api.RequestListener;
import com.blstream.studybox.api.RestClientManager;
import com.blstream.studybox.auth.login.LoginManager;
import com.blstream.studybox.model.database.Card;
import com.blstream.studybox.model.database.Decks;

import java.util.List;

import retrofit.RetrofitError;

public class DataHelper implements DataProvider {
    private List<Card> downloadedCards;
    private List<Decks> publicDecks;
    private static final String DECKS_KEY = "decks";

    @Override
    public List<Decks> getDecks() {
        return Decks.getAll();
    }

    public List<Card> getFlashcards() {
        return downloadedCards;
    }

    public List<Decks> getPublicDecks() {
        return publicDecks;
    }

    public void downloadFlashcards(final String deckId, final String randomAmount, final RequestListener<String> listener) {
        RestClientManager.getFlashcards(deckId, randomAmount, new RequestCallback<>(new RequestListener<List<Card>>() {
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
        new Delete().from(Decks.class).execute();
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

    public void downloadPublicDecks(final RequestListener<String> listener) {
        RestClientManager.getPublicDecks(DECKS_KEY, new RequestCallback<>(new RequestListener<List<Decks>>() {
            @Override
            public void onSuccess(List<Decks> response) {
                publicDecks = response;
                listener.onSuccess("New decks or nothing new");
            }

            @Override
            public void onFailure(RetrofitError error) {
                listener.onFailure(error);
            }
        }));
    }

    private void saveDecksToDataBase(List<Decks> decks) {
        for (Decks deck : decks) {
            deck.save();
        }
    }
}
