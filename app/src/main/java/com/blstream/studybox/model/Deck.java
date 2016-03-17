
package com.blstream.studybox.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Deck {

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
    private List<Card> cards = new ArrayList<Card>();

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

}
