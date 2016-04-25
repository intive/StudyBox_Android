package com.blstream.studybox.exam_view;

import com.hannesdorfmann.mosby.mvp.MvpView;

public interface ExamView extends MvpView {

    void showCardCounter(int currentCard, int totalCards);
    void startEmptyDeckActivity();
}
