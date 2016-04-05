package com.blstream.studybox.model.database;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Łukasz on 2016-03-16.
 */
@Table(name = "Decks")
public class Deck extends Model implements Parcelable{

    @Expose
    @Column(name = "DeckNo", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private Integer deckNo;

    @Expose
    @Column(name = "DeckName")
    private String deckName;

    @Expose
    @Column(name = "NoOfQuestions")
    private Integer noOfQuestions;

    @Expose
    @SerializedName("cards")
    private List<Card> cards;

    public Deck() {
        super();
    }

    public Deck(Integer deckNo, String deckName, Integer noOfQuestions) {
        this.deckNo = deckNo;
        this.deckName = deckName;
        this.noOfQuestions = noOfQuestions;
    }

    public List<Card> getCardsList() {
        return cards;
    }

    public void setCardsList(List<Card> cardsList) {
        cards = cardsList;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(deckNo);
        dest.writeString(deckName);
        dest.writeInt(noOfQuestions);
        dest.writeTypedList(cards);
    }

    public static final Creator<Deck> CREATOR = new Creator<Deck>() {
        @Override
        public Deck createFromParcel(Parcel source) {
            return new Deck(source);
        }

        @Override
        public Deck[] newArray(int size) {
            return new Deck[size];
        }
    };

    private Deck(Parcel source) {
        deckNo = source.readInt();
        deckName = source.readString();
        noOfQuestions = source.readInt();

        cards = new ArrayList<>();
        source.readTypedList(cards, Card.CREATOR);
    }
}
