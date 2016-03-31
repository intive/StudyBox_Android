package com.blstream.studybox.database;

import com.blstream.studybox.model.database.Card;
import com.blstream.studybox.model.database.Deck;
import com.blstream.studybox.model.database.DecksList;

import java.util.List;

/**
 * Created by Łukasz on 2016-03-18.
 */
public interface DataProvider {

    DecksList getAllDecks();

    Deck getSingleDeck(int deckNumber);

    Deck getSingleDeck(String deckName);

    List<Card> getAllCards(int deckNumber);

    List<Card> getAllCards(String deckName);

    Card getSingleCard(int deckNumber, int questionNumber);

}
