package com.blstream.studybox.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Deck implements Parcelable{

    @SerializedName("deckNo")
    @Expose
    private Integer deckNo;
    @SerializedName("deckName")
    @Expose
    private String deckName;
    @SerializedName("noOfQuestions")
    @Expose
    private Integer noOfQuestions;
    @SerializedName("cards")
    @Expose
    private List<Card> cards;

    // TODO: delete, its only for time when change model
    public Deck(int dNo, String dName, Integer nOfQue, List<Card> crds) {
        deckNo = dNo;
        deckName = dName;
        noOfQuestions = nOfQue;
        cards = crds;
    }

    /**
     * 
     * @return
     *     The deckNo
     */
    public Integer getDeckNo() {
        return deckNo;
    }

    /**
     * 
     * @param deckNo
     *     The deckNo
     */
    public void setDeckNo(Integer deckNo) {
        this.deckNo = deckNo;
    }

    /**
     * 
     * @return
     *     The deckName
     */
    public String getDeckName() {
        return deckName;
    }

    /**
     * 
     * @param deckName
     *     The deckName
     */
    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }

    /**
     * 
     * @return
     *     The noOfQuestions
     */
    public Integer getNoOfQuestions() {
        return noOfQuestions;
    }

    /**
     * 
     * @param noOfQuestions
     *     The noOfQuestions
     */
    public void setNoOfQuestions(Integer noOfQuestions) {
        this.noOfQuestions = noOfQuestions;
    }

    /**
     * 
     * @return
     *     The cards
     */
    public List<Card> getCards() {
        return cards;
    }

    /**
     * 
     * @param cards
     *     The cards
     */
    public void setCards(List<Card> cards) {
        this.cards = cards;
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

        cards = new ArrayList<Card>();
        source.readTypedList(cards, Card.CREATOR);
    }
}
