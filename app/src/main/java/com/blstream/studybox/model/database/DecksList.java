package com.blstream.studybox.model.database;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class DecksList {

    @SerializedName("decks")
    @Expose
    private List<Deck> decks = new ArrayList<Deck>();

    public DecksList() {
    }

    public DecksList(List<Deck> decks) {
        this.decks = decks;
    }

    /**
     * @return The decks
     */
    public List<Deck> getDecks() {
        return decks;
    }

    /**
     * @param decks The decks
     */
    public void setDecks(List<Deck> decks) {
        this.decks = decks;
    }

    public Boolean isEmpty() {
        return  decks == null || decks.isEmpty();
    }
}
