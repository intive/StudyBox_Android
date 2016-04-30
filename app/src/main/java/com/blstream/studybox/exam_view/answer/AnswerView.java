package com.blstream.studybox.exam_view.answer;

import com.hannesdorfmann.mosby.mvp.MvpView;

public interface AnswerView extends MvpView {

    void showTextAnswer(String answer);
    void showImageAnswer(String url);
}
