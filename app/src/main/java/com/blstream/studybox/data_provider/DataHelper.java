package com.blstream.studybox.data_provider;

import android.content.Context;

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

    private Context context;
    private List<Deck> currentDecks;

    public DataHelper(Context context) {
        this.context = context;
    }

    @Override
    public void fetchPrivateDecks(final DataProvider.OnDecksReceivedListener<List<Deck>> listener) {
        RestClientManager.getPrivateDecks(true,
                new AuthRequestInterceptor(new LoginManager(context).getCredentials()),
                new RequestCallback<>(new RequestListener<List<Deck>>() {

                    @Override
                    public void onSuccess(List<Deck> response) {
                        setCurrentDecks(response);
                        saveDecksToDataBase(response);
                        listener.OnDecksReceived(response);
                    }

                    @Override
                    public void onFailure(RetrofitError error) {

                    }
                }));
    }

    @Override
    public void fetchPublicDecks(final DataProvider.OnDecksReceivedListener<List<Deck>> listener, final String onEmptyResponseMessage) {
        RestClientManager.getPublicDecks(true, new RequestCallback<>(new RequestListener<List<Deck>>() {
            @Override
            public void onSuccess(List<Deck> response) {
                if (isNullOrEmpty(response)) {
                    listener.OnEmptyResponse(onEmptyResponseMessage);
                    return;
                }

                setCurrentDecks(response);
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
    public void fetchRandomDeck(final DataProvider.OnDecksReceivedListener<Deck> listener) {
        RestClientManager.getRandomDeck(true, new RequestCallback<>(new RequestListener<Deck>() {
            @Override
            public void onSuccess(Deck response) {
                listener.OnDecksReceived(response);
            }

            @Override
            public void onFailure(RetrofitError error) {
            }
        }));
    }

    @Override
    public void fetchDecksByName(final OnDecksReceivedListener<List<Deck>> listener, String deckName, final String onEmptyResponseMessage) {
        RestClientManager.getDecksByName(deckName, true, new RequestCallback<>(new RequestListener<List<Deck>>() {
            @Override
            public void onSuccess(List<Deck> response) {
                if (isNullOrEmpty(response)) {
                    listener.OnEmptyResponse(onEmptyResponseMessage);
                    return;
                }

                setCurrentDecks(response);
                listener.OnDecksReceived(response);
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
