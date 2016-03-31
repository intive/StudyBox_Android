package com.blstream.studybox.model.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Łukasz on 2016-03-16.
 */
@Table(name = "Decks")
public class Deck extends Model {

    @Expose
    @Column(name = "DeckNo", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public Integer deckNo;

    @Expose
    @Column(name = "DeckName")
    public String deckName;

    @Expose
    @Column(name = "NoOfQuestions")
    public Integer noOfQuestions;

    @Expose
    @SerializedName("cards")
    public List<Card> cards;

    public Deck() {
        super();
    }

    public Deck(Integer deckNo, String deckName, Integer noOfQuestions) {
        this.deckNo = deckNo;
        this.deckName = deckName;
        this.noOfQuestions = noOfQuestions;
    }

    public List<Card> getCards() {
        return getMany(Card.class, "Deck");
    }

    public static List<Deck> all() {
        return new Select().from(Deck.class).execute();
    }

    public Integer getDeckNo() {
        return deckNo;
    }

    public String getDeckName() {
        return deckName;
    }

    public Integer getNoOfQuestions() {
        return noOfQuestions;
    }

    public void setDeckNo(Integer deckNo) {
        this.deckNo = deckNo;
    }

    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }

    public void setNoOfQuestions(Integer noOfQuestions) {
        this.noOfQuestions = noOfQuestions;
    }

}
