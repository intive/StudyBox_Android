package com.blstream.studybox.database;

import com.blstream.studybox.model.database.Card;
import com.blstream.studybox.model.database.Decks;

import java.util.List;

public interface DataProvider {

    void fetchPrivateDecks(OnDecksReceivedListener listener, String onEmptyResponseMessage);

    void fetchPublicDecks(OnDecksReceivedListener listener, String onEmptyResponseMessage);

    void fetchFlashcards(String deckId, String randomAmount, OnCardsReceivedListener<List<Card>> listener);

    void fetchTips(String deckId, String cardId, OnTipsReceivedListener listener);

    void fetchRandomDeck(OnDecksReceivedListener<Decks> listener);

    void fetchDecksByName(OnDecksReceivedListener<List<Decks>> listener, String deckName, String onEmptyResponseMessage);

    interface OnDecksReceivedListener<T> {
        void OnDecksReceived(T decks);

        void OnEmptyResponse(String message);
    }

    interface OnCardsReceivedListener<T> {
        void OnCardsReceived(T cards);
    }

    interface OnTipsReceivedListener<T> {
        void OnTipsReceived(T tips);
    }

    List<Decks> getPrivateDecks();

    List<Decks> getPublicDecks();
}
