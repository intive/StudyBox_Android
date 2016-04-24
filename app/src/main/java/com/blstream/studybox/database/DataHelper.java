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

import java.text.DecimalFormatSymbols;
import java.util.List;

import retrofit.RetrofitError;

public class DataHelper implements DataProvider {
    private List<Card> downloadedCards;
    private List<Decks> publicDecks;
    private Decks randomDeck;
    private static final String DECKS_KEY = "decks";
    private static final String RANDOM_DECK_KEY = "decks?random=true";

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

    public Decks getRandomDeck() {
        return randomDeck;
    }

    public void downloadFlashcard(String deckId, final RequestListener<String> listener) {
        RestClientManager.getFlashcards(deckId, new RequestCallback<>(new RequestListener<List<Card>>() {
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

    public void downloadRandomDeck(final RequestListener<String> listener) {
        RestClientManager.getRandomDeck(RANDOM_DECK_KEY, new RequestCallback<>(new RequestListener<Decks>() {
            @Override
            public void onSuccess(Decks response) {
                randomDeck = response;
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
