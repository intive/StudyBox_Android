package com.blstream.studybox.database;

import com.blstream.studybox.model.database.Card;
import com.blstream.studybox.model.database.Decks;

import java.util.List;

public interface DataProvider {

    List<Decks> getDecks();
    List<Card> getFlashcards();
    List<Decks> getPublicDecks();

}
