package com.blstream.studybox.exam.exam_view;

import com.hannesdorfmann.mosby.mvp.MvpView;

public interface ExamView extends MvpView {

    void setCardCounter(int currentCard, int totalCards);
    void startEmptyDeckActivity();
    void showQuestion(String cardId);
    void showAnswer(String cardId);
    void showResult(int correctAnswers, int totalCards);
}
