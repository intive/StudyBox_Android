package com.blstream.studybox.data_provider;

import android.content.res.Resources;

import com.blstream.studybox.R;
import com.blstream.studybox.api.AuthRequestInterceptor;
import com.blstream.studybox.api.RequestCallback;
import com.blstream.studybox.api.RequestListener;
import com.blstream.studybox.api.RestClientManager;
import com.blstream.studybox.auth.login.LoginManager;
import com.blstream.studybox.debugger.DebugHelper;
import com.blstream.studybox.model.database.Card;
import com.blstream.studybox.model.database.Deck;
import com.blstream.studybox.model.database.Tip;

import java.util.Collection;
import java.util.List;

import retrofit.RetrofitError;

public class DataHelper implements DataProvider {

    private List<Deck> currentDecks;
    private Resources resources;

    public DataHelper() {}

    public DataHelper(Resources resources) {
        this.resources = resources;
    }

    @Override
    public void fetchPrivateDecks(final DataProvider.OnDecksReceivedListener<List<Deck>> listener) {
        RestClientManager.getPrivateDecks(true,
                new AuthRequestInterceptor(new LoginManager().getCredentials()),
                new RequestCallback<>(new RequestListener<List<Deck>>() {

                    @Override
                    public void onSuccess(List<Deck> response) {
                        setCurrentDecks(response);
                        saveDecksToDataBase(response);
                        listener.OnDecksReceived(response, true);
                    }

                    @Override
                    public void onFailure(RetrofitError error) {
                        listener.OnEmptyResponse(resources.getString(R.string.decks_download_error));
                    }
                }));
    }

    @Override
    public void fetchPublicDecks(final DataProvider.OnDecksReceivedListener<List<Deck>> listener) {
        RestClientManager.getPublicDecks(true, new RequestCallback<>(new RequestListener<List<Deck>>() {
            @Override
            public void onSuccess(List<Deck> response) {
                if (isNullOrEmpty(response)) {
                    listener.OnEmptyResponse(resources.getString(R.string.no_decks));
                    return;
                }

                setCurrentDecks(response);
                listener.OnDecksReceived(response, false);
            }

            @Override
            public void onFailure(RetrofitError error) {
                listener.OnEmptyResponse(resources.getString(R.string.decks_download_error));
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
    public void fetchRandomDeck(final DataProvider.OnDecksReceivedListener<Deck> listener) {
        RestClientManager.getRandomDeck(true, new RequestCallback<>(new RequestListener<Deck>() {
            @Override
            public void onSuccess(Deck response) {
                listener.OnDecksReceived(response, false);
            }

            @Override
            public void onFailure(RetrofitError error) {
            }
        }));
    }

    @Override
    public void fetchDecksByNameLoggedIn(final OnDecksReceivedListener<List<Deck>> listener, String deckName) {
        RestClientManager.getDecksByNameLoggedIn(deckName, true,
                new AuthRequestInterceptor(new LoginManager().getCredentials()),
                new RequestCallback<>(new RequestListener<List<Deck>>() {

                    @Override
                    public void onSuccess(List<Deck> response) {
                        if (isNullOrEmpty(response)) {
                            listener.OnEmptyResponse(resources.getString(R.string.no_decks_query));
                            return;
                        }

                        setCurrentDecks(response);
                        listener.OnDecksReceived(response, false);
                    }

                    @Override
                    public void onFailure(RetrofitError error) {
                        DebugHelper.logString(error.getMessage());
                    }
                }));
    }

    @Override
    public void fetchDecksByName(final OnDecksReceivedListener<List<Deck>> listener, String deckName) {
        RestClientManager.getDecksByName(deckName, true, new RequestCallback<>(new RequestListener<List<Deck>>() {
            @Override
            public void onSuccess(List<Deck> response) {
                if (isNullOrEmpty(response)) {
                    listener.OnEmptyResponse(resources.getString(R.string.no_decks_query));
                    return;
                }

                setCurrentDecks(response);
                listener.OnDecksReceived(response, false);
            }

            @Override
            public void onFailure(RetrofitError error) {
                DebugHelper.logString(error.getMessage());
            }
        }));
    }

    @Override
    public void fetchTips(String deckId, String cardId, final OnTipsReceivedListener<List<Tip>> listener) {
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
    public List<Deck> getCurrentDecks() {
        return currentDecks;
    }

    private void setCurrentDecks(List<Deck> currentDecks) {
        this.currentDecks = currentDecks;
    }

    private void saveDecksToDataBase(List<Deck> decks) {
        for (Deck deck : decks) {
            deck.save();
        }
    }

    private void saveCardsToDataBase(List<Card> cards) {
        for (Card card : cards) {
            card.save();
        }
    }

    public boolean isNullOrEmpty(final Collection<?> c) {
        return c == null || c.isEmpty();
    }
}
