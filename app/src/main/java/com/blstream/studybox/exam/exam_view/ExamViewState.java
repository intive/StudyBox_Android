package com.blstream.studybox.exam.exam_view;

import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

public class ExamViewState<V extends ExamView> implements ViewState<V> {

    private int currentCard;
    private int totalCards;
    private String deckTitle;

    public void saveCardCounter(int currentCard, int totalCards) {
        this.currentCard = currentCard;
        this.totalCards = totalCards;
    }

    public void saveDeckTitle(String deckTitle) {
        this.deckTitle = deckTitle;
    }

    @Override
    public void apply(ExamView view, boolean retained) {
        view.setCardCounter(currentCard, totalCards);
        view.setDeckTitle(deckTitle);
    }
}
