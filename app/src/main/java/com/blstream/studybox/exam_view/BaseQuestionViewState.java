package com.blstream.studybox.exam_view;

import com.hannesdorfmann.mosby.mvp.MvpView;
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

public class BaseQuestionViewState<V extends MvpView> implements ViewState<V> {

    protected final static int STATE_HIDE_PROMPT = 0;
    protected final static int STATE_SHOW_PROMPT = 1;

    protected int state = STATE_HIDE_PROMPT;

    public void setHidePrompt() {
        state = STATE_HIDE_PROMPT;
    }

    public void setShowPrompt() {
        state = STATE_SHOW_PROMPT;
    }

    @Override
    public void apply(V view, boolean retained) {
        switch (state) {
            case STATE_HIDE_PROMPT:
                //TODO view.hidePrompt
                break;
            case STATE_SHOW_PROMPT:
                //TODO view.showPrompt
                break;
        }
    }
}
