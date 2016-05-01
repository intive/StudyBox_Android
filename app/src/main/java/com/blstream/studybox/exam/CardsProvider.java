package com.blstream.studybox.exam;

import com.blstream.studybox.model.database.Card;

import java.util.ArrayList;
import java.util.List;

public class CardsProvider {

    private List<Card> flashcards;
    private Card currentCard;
    private int position;

    public CardsProvider(List<Card> flashcards) {
        this.flashcards = new ArrayList<>(flashcards);
        position = 0;
    }

    public int getTotalCardsNumber() {
        return flashcards.size();
    }

    public int getCurrentCardNumber() {
        return position + 1;
    }

    public Card getCurrentCard() {
        return currentCard;
    }

    public String getQuestion() {
        return currentCard.getQuestion();
    }

    public String getAnswer() {
        return currentCard.getAnswer();
    }

    public void resetPosition() {
        position = 0;
    }

    public int getPosition() {
        return position;
    }

    public boolean setNextCard() {
        position++;
        if (position < flashcards.size()) {
            currentCard = flashcards.get(position);
            return true;
        } else {
            return false;
        }
    }
}
