package com.blstream.studybox.database;

import android.content.Context;

import com.blstream.studybox.api.AuthRequestInterceptor;
import com.blstream.studybox.api.RequestCallback;
import com.blstream.studybox.api.RequestListener;
import com.blstream.studybox.api.RestClientManager;
import com.blstream.studybox.auth.login.LoginManager;
import com.blstream.studybox.model.database.Card;
import com.blstream.studybox.model.database.Decks;
import com.blstream.studybox.model.database.Tip;

import java.util.List;

import retrofit.RetrofitError;

public class DataHelper implements DataProvider {

    private Context context;
    private List<Decks> privateDecks;
    private List<Decks> publicDecks;

    public DataHelper(Context context) {
        this.context = context;
    }

    @Override
    public void fetchPrivateDecks(final DataProvider.OnDecksReceivedListener listener) {
        RestClientManager.getDecks(true,
                new AuthRequestInterceptor(new LoginManager(context).getCredentials()),
                new RequestCallback<>(new RequestListener<List<Decks>>() {

            @Override
            public void onSuccess(List<Decks> response) {
                privateDecks = response;
                saveDecksToDataBase(response);
                listener.OnDecksReceived(response);
            }

            @Override
            public void onFailure(RetrofitError error) {

            }
        }));

    }

    @Override
    public void fetchPublicDecks(final DataProvider.OnDecksReceivedListener listener) {
        RestClientManager.getPublicDecks(true, new RequestCallback<>(new RequestListener<List<Decks>>() {
            @Override
            public void onSuccess(List<Decks> response) {
                publicDecks = response;
                listener.OnDecksReceived(response);
            }

            @Override
            public void onFailure(RetrofitError error) {

            }
        }));
    }

    @Override
    public void fetchFlashcards(String deckId, String randomAmount, final OnCardsReceivedListener<List<Card>> listener) {
        RestClientManager.getFlashcards(deckId, randomAmount, new RequestCallback<>(new RequestListener<List<Card>>() {
            @Override
            public void onSuccess(List<Card> response) {
                saveCardsToDataBase(response);
                listener.OnCardsReceived(response);
            }

            @Override
            public void onFailure(RetrofitError error) {

            }
        }));

    }

    @Override
    public void fetchRandomDeck(final DataProvider.OnDecksReceivedListener<List<Decks>> listener) {
        RestClientManager.getRandomDeck(true, true, new RequestCallback<>(new RequestListener<List<Decks>>() {
            @Override
            public void onSuccess(List<Decks> response) {
                listener.OnDecksReceived(response);
            }

            @Override
            public void onFailure(RetrofitError error) {

            }
        }));
    }

    @Override
    public void fetchTips(String deckId, String cardId, final OnTipsReceivedListener listener) {
        RestClientManager.getTips(deckId, cardId, new RequestCallback<>(new RequestListener<List<Tip>>() {
            @Override
            public void onSuccess(List<Tip> response) {
                listener.OnTipsReceived(response);
            }

            @Override
            public void onFailure(RetrofitError error) {

            }
        }));
    }

    @Override
    public List<Decks> getPrivateDecks() {
        return privateDecks;
    }

    @Override
    public List<Decks> getPublicDecks() {
        return publicDecks;
    }

    private void saveDecksToDataBase(List<Decks> decks) {
        for (Decks deck : decks) {
            deck.save();
        }
    }

    private void saveCardsToDataBase(List<Card> cards) {
        for (Card card : cards) {
            card.save();
        }
    }
}
