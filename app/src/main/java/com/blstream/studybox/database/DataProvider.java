package com.blstream.studybox.database;

import com.blstream.studybox.model.database.Card;
import com.blstream.studybox.model.database.Decks;

import java.util.List;

/**
 * Created by Łukasz on 2016-03-18.
 */
public interface DataProvider {

    List<Decks> getDecks();
    List<Card> getFlashcards();
    List<Decks> getPublicDecks();

}
