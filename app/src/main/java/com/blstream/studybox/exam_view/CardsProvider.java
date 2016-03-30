package com.blstream.studybox.exam_view;


import com.blstream.studybox.activities.ExamActivity;

public class CardsProvider {

    private final ExamActivity.Deck deck;
    private ExamActivity.Card currentCard;
    private ExamActivity.Card laterCard;
    private final int preloadImageCount;
    private int position;
    private String[] answers;
    private String[] questions;
    private final String prompt;

    public CardsProvider (ExamActivity.Deck deck, int preloadImageCount) {
        this.deck = deck;
        this.preloadImageCount = preloadImageCount;
        answers = new String[preloadImageCount];
        questions = new String[preloadImageCount];
        prompt = deck.cards.get(0).prompt;
        setFirstImgs();
    }

    private void setFirstImgs(){
        answers = new String[preloadImageCount];
        questions = new String[preloadImageCount];
        ExamActivity.Card card;
        for(int i = 0; i < preloadImageCount; i++) {
            card = deck.cards.get(i);
            answers[i] = card.answer;
            questions[i] = card.question;
        }
    }

    public String[] getFirstAnswers(){
        return answers;
    }

    public String[] getFirstQuestions(){
        return questions;
    }

    public String getFirstPrompt() {
        return prompt;
    }

    private void updatePosition(){
        position++;
    }

    public String getNextQuestion(){
        return currentCard.question;
    }

    public String getNextPrompt(){
        return currentCard.prompt;
    }

    public String getNextAnswer(){
        return currentCard.answer;
    }

    public String getLaterQuestion() {
        return laterCard.question;
    }

    public String getLaterAnswer(){
        return laterCard.answer;
    }

    public void initOnRestart() {
        position = 0;
    }

    public int getPosition(){
        return position;
    }

    public void changeCard(){
        updatePosition();
        if(deck.numberOfQuestions > position ) {
            setCards();
        }
    }

    private void setCards(){
        currentCard = deck.cards.get(position);
        if (deck.numberOfQuestions > position + preloadImageCount - 1) {
            laterCard = deck.cards.get(position + preloadImageCount - 1);
        }
    }
}
