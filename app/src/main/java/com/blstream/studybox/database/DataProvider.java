package com.blstream.studybox.database;

import com.blstream.studybox.model.database.Card;
import com.blstream.studybox.model.database.Decks;

import java.util.List;

public interface DataProvider {

    void fetchPrivateDecks(OnDecksReceivedListener listener);

    void fetchPublicDecks(OnDecksReceivedListener listener);

    void fetchFlashcards(OnCardsReceivedListener<List<Card>> listener, String deckId);

    void fetchRandomDeck(OnDecksReceivedListener<List<Decks>> listener);

    interface OnDecksReceivedListener<T> {
        void OnDecksReceived(T decks);
    }

    interface OnCardsReceivedListener<T> {
        void OnCardsReceived(T cards);
    }

    List<Decks> getPrivateDecks();

    List<Decks> getPublicDecks();
}
