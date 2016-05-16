package com.blstream.studybox.data_provider;

import com.blstream.studybox.model.database.Card;
import com.blstream.studybox.model.database.Deck;

import java.util.List;

public interface DataProvider {

    void fetchPrivateDecks(OnDecksReceivedListener<List<Deck>> listener, String onEmptyResponseMessage);

    void fetchPublicDecks(OnDecksReceivedListener<List<Deck>> listener, String onEmptyResponseMessage);

    void fetchFlashcards(String deckId, String randomAmount, OnCardsReceivedListener<List<Card>> listener);

    void fetchTips(String deckId, String cardId, OnTipsReceivedListener listener);

    void fetchRandomDeck(OnDecksReceivedListener<Deck> listener);

    void fetchDecksByName(OnDecksReceivedListener<List<Deck>> listener, String deckName, String onEmptyResponseMessage);

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

    List<Deck> getPrivateDecks();

    List<Deck> getPublicDecks();

    List<Deck> getCurrentDecks();
}
