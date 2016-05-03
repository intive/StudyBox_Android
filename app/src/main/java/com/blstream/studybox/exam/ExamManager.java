package com.blstream.studybox.exam;

import android.support.annotation.NonNull;

import com.blstream.studybox.model.database.Card;

import java.util.ArrayList;
import java.util.List;

public class ExamManager {

    private List<Card> flashcards;
    private List<Card> wrongAnsweredCards;
    private final List<Card> allCards;
    private Card currentCard;
    private int correctAnswers;
    private int position;

    public ExamManager(@NonNull List<Card> flashcards) {
        this.flashcards = new ArrayList<>(flashcards);
        this.allCards = new ArrayList<>(flashcards);
        init();
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

    public String getCurrentCardId() {
        return currentCard.getCardId();
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCurrentCardCorrect() {
        correctAnswers++;
    }

    public void setCurrentCardWrong() {
        wrongAnsweredCards.add(flashcards.get(position));
    }

    public void setFlashcards(@NonNull List<Card> flashcards) {
        this.flashcards = flashcards;
        init();
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

    public void improveAll() {
        flashcards = new ArrayList<>(allCards);
        init();
    }

    public void improveWrong() {
        flashcards = new ArrayList<>(wrongAnsweredCards);
        init();
    }

    private void init() {
        wrongAnsweredCards = new ArrayList<>();
        position = 0;
        correctAnswers = 0;
        currentCard = flashcards.get(position);
    }
}
