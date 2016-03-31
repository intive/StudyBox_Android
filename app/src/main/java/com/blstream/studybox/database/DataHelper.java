package com.blstream.studybox.database;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.blstream.studybox.Constants;
import com.blstream.studybox.api.RequestCallback;
import com.blstream.studybox.api.RequestListener;
import com.blstream.studybox.api.RestClientManager;
import com.blstream.studybox.model.database.Card;
import com.blstream.studybox.model.database.Deck;
import com.blstream.studybox.model.database.DecksList;

import java.util.List;

import retrofit.RetrofitError;

/**
 * Created by Łukasz on 2016-03-26.
 */
public class DataHelper implements DataProvider {
    @Override
    public DecksList getAllDecks() {
        return new DecksList(Deck.all());
    }

    @Override
    public Deck getSingleDeck(int deckNumber) {
        Deck deck = new Select()
                .from(Deck.class)
                .where("DeckNO = ?", deckNumber)
                .executeSingle();

        return deck;
    }

    @Override
    public Deck getSingleDeck(String deckName) {
        Deck deck = new Select()
                .from(Deck.class)
                .where("DeckName = ?", deckName)
                .executeSingle();

        return deck;
    }

    @Override
    public List<Card> getAllCards(int deckNumber) throws NullPointerException {
        Deck deck = new Select()
                .from(Deck.class)
                .where("DeckNO = ?", deckNumber)
                .executeSingle();

        return deck.getCards();
    }

    @Override
    public List<Card> getAllCards(String deckName) throws NullPointerException {
        Deck deck = new Select()
                .from(Deck.class)
                .where("DeckName = ?", deckName)
                .executeSingle();

        return deck.getCards();
    }

    @Override
    public Card getSingleCard(int deckNumber, int questionNumber) throws NullPointerException {
        Deck deck = new Select()
                .from(Deck.class)
                .where("DeckNO = ?", deckNumber)
                .executeSingle();

        return deck.getCards().get(questionNumber + 1);
    }

    public void deleteAllDecks() {
        new Delete().from(Deck.class).execute();
    }

    public void downloadData(final RequestListener<String> listener) {
        RestClientManager.getAllDecks(Constants.API_KEY, new RequestCallback<>(new RequestListener<DecksList>() {
            @Override
            public void onSuccess(DecksList response) {
                saveToDataBase(response);
                listener.onSuccess("New decks or nothing new");
            }

            @Override
            public void onFailure(RetrofitError error) {

            }
        }));
    }

    private void saveToDataBase(DecksList decksList) {
        List<Deck> deckList = decksList.getDecks();
        for (Deck deck : deckList) {

            deck.save();

            for (Card card : deck.getCardsList()) {
                card.deck = deck;
                card.save();
            }
        }
    }
}
