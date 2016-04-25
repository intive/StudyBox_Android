package com.blstream.studybox.exam_view;

import com.blstream.studybox.model.database.Card;

import java.util.List;

public class CardsProvider {

    private static final String TAG_PROMPT = "PROMPT";
    private List<Card> flashcards;
    private Card currentCard;
    private Card laterCard;
    private int preloadImageCount;
    private int position;

    public CardsProvider(List<Card> flashcards) {
        this.flashcards = flashcards;
    }

    public CardsProvider(List<Card> flashcards, int preloadImageCount) {
        this.flashcards = flashcards;
        this.preloadImageCount = preloadImageCount;
    }

    public void changeFlashcards(List<Card> flashcards, int preloadImageCount){
        this.flashcards = flashcards;
        this.preloadImageCount = preloadImageCount;
    }

    public String[] getAnswersForPreload() {
        String[] answersForPreload = new String[preloadImageCount];
        for (int i = 0; i < preloadImageCount; i++) {
            if (flashcards.size() > position + i) {
                answersForPreload[i] = flashcards.get(position + i).getAnswer();
            }
        }
        return answersForPreload;
    }

    public String[] getQuestionsForPreload() {
        String[] questionsForPreload = new String[preloadImageCount];
        for (int i = 0; i < preloadImageCount; i++) {
            if (flashcards.size() > position + i) {
                questionsForPreload[i] = flashcards.get(position + i).getQuestion();
            }
        }
        return questionsForPreload;
    }

    public String getFirstPrompt() {
        return TAG_PROMPT;
    }

    private void updatePosition() {
        position++;
    }

    public String getNextQuestion() {
        return currentCard.getQuestion();
    }

    public String getNextPrompt() {
        return TAG_PROMPT;
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
}
