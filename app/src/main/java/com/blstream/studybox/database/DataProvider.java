package com.blstream.studybox.database;

import com.blstream.studybox.model.database.Decks;

import java.util.List;

public interface DataProvider {

    void fetchPrivateDecks(OnDecksReceivedListener listener);

    void fetchPublicDecks(OnDecksReceivedListener listener);

    void fetchFlashcards(String deckId, OnCardsReceivedListener listener);

    void fetchTips(String deckId, String cardId, OnTipsReceivedListener listener);

    void fetchRandomDeck(OnDecksReceivedListener<List<Decks>> listener);

    interface OnDecksReceivedListener<T> {
        void OnDecksReceived(T decks);
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
