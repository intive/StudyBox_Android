package com.blstream.studybox.exam_view;


import com.blstream.studybox.model.Card;
import com.blstream.studybox.model.Deck;

public class CardsProvider {

    private final Deck deck;
    private Card currentCard;
    private Card laterCard;
    private final int preloadImageCount;
    private int position;
    private String[] answers;
    private String[] questions;
    private final String prompt;

    public CardsProvider(Deck deck, int preloadImageCount) {
        this.deck = deck;
        this.preloadImageCount = preloadImageCount;
        answers = new String[preloadImageCount];
        questions = new String[preloadImageCount];
        prompt = deck.getCards().get(0).getPrompt();
        setFirstImages();
    }

    private void setFirstImages() {
        answers = new String[preloadImageCount];
        questions = new String[preloadImageCount];
        Card card;
        for (int i = 0; i < preloadImageCount; i++) {
            card = deck.getCards().get(i);
            answers[i] = card.getAnswer();
            questions[i] = card.getQuestion();
        }
    }

    public String[] getFirstAnswers() {
        return answers;
    }

    public String[] getFirstQuestions() {
        return questions;
    }

    public String getFirstPrompt() {
        return prompt;
    }

    private void updatePosition() {
        position++;
    }

    public String getNextQuestion() {
        return currentCard.getQuestion();
    }

    public String getNextPrompt() {
        return currentCard.getPrompt();
    }

    public String getNextAnswer() {
        return currentCard.getAnswer();
    }

    public String getLaterQuestion() {
        if (laterCard == null) {
            return null;
        }
        return laterCard.getQuestion();
    }

    public String getLaterAnswer() {
        if (laterCard == null) {
            return null;
        }
        return laterCard.getAnswer();
    }

    public void initOnRestart() {
        position = 0;
    }

    public int getPosition() {
        return position;
    }

    public void changeCard() {
        updatePosition();
        if (deck.getNoOfQuestions() > position) {
            setCards();
        }
    }

    private void setCards() {
        currentCard = deck.getCards().get(position);
        if (deck.getNoOfQuestions() > position + preloadImageCount - 1) {
            laterCard = deck.getCards().get(position + preloadImageCount - 1);
        }
    }
}
