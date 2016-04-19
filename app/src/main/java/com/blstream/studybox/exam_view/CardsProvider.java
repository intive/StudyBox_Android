package com.blstream.studybox.exam_view;


import com.blstream.studybox.model.database.Card;

import java.util.List;

public class CardsProvider {

    private final List<Card> flashcards;
    private Card currentCard;
    private Card laterCard;
    private final int preloadImageCount;
    private int position;
    private String[] answers;
    private String[] questions;
    private final String prompt;

    public CardsProvider(List<Card> flashcards, int preloadImageCount) {
        this.flashcards = flashcards;
        this.preloadImageCount = preloadImageCount;
        answers = new String[preloadImageCount];
        questions = new String[preloadImageCount];
        prompt = "PROMPT";
        setFirstImages();
    }

    private void setFirstImages() {
        answers = new String[preloadImageCount];
        questions = new String[preloadImageCount];
        Card card;
        for (int i = 0; i < preloadImageCount; i++) {
            card = flashcards.get(i);
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
        return prompt;
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
        if (flashcards.size() > position) {
            setCards();
        }
    }

    private void setCards() {
        currentCard = flashcards.get(position);
        if (flashcards.size() > position + preloadImageCount - 1) {
            laterCard = flashcards.get(position + preloadImageCount - 1);
        }
    }

    public Card getCurrentCard(){
        return flashcards.get(position);
    }
}
