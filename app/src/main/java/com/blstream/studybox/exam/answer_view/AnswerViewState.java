package com.blstream.studybox.exam.answer_view;

import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

public class AnswerViewState<V extends AnswerView> implements ViewState<V> {

    protected final static int STATE_SHOW_ANSWER_TEXT = 0;
    protected final static int STATE_SHOW_ANSWER_IMAGE = 1;

    protected String answer;

    protected int state = STATE_SHOW_ANSWER_TEXT;

    public void setStateShowAnswerText(String answer) {
        this.answer = answer;
        state = STATE_SHOW_ANSWER_TEXT;
    }

    public void setStateShowAnswerImage(String answer) {
        this.answer = answer;
        state = STATE_SHOW_ANSWER_IMAGE;
    }

    @Override
    public void apply(V view, boolean retained) {
        switch (state) {
            case STATE_SHOW_ANSWER_TEXT:
                view.showTextAnswer(answer);
                break;
            case STATE_SHOW_ANSWER_IMAGE:
                view.showImageAnswer(answer);
                break;
        }
    }
}
