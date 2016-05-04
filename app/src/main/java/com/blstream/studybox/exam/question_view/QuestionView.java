package com.blstream.studybox.exam.question_view;

import com.hannesdorfmann.mosby.mvp.MvpView;

public interface QuestionView extends MvpView {

    void showTextQuestion(String question);
    void showImageQuestion(String url);
    void showTextPrompt(String prompt);
    void showImagePrompt(String url);
    void disablePrompt();
    void enablePrompt();
}
