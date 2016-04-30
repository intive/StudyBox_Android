package com.blstream.studybox.exam_view;

import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

public class ExamViewState<V extends ExamView> implements ViewState<V> {

    protected int currentCard;
    protected int totalCards;

    public void saveCardCounter(int currentCard, int totalCards) {
        this.currentCard = currentCard;
        this.totalCards = totalCards;
    }

    @Override
    public void apply(ExamView view, boolean retained) {
        view.setCardCounter(currentCard, totalCards);
    }
}
