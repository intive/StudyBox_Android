package com.blstream.studybox.auth;

import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

public class BaseAuthViewState<V extends BaseAuthView> implements ViewState<V> {

    protected final int STATE_SHOW_FORM = 0;
    protected final int STATE_SHOW_LOADING = 1;
    protected final int STATE_SHOW_ERROR = 2;

    protected int state = STATE_SHOW_FORM;

    public void setShowForm() {
        state = STATE_SHOW_FORM;
    }

    public void setShowLoading() {
        state = STATE_SHOW_LOADING;
    }

    public void setShowError() {
        state = STATE_SHOW_ERROR;
    }

    @Override
    public void apply(V view, boolean retained) {
        switch (state) {
            case STATE_SHOW_FORM:
                view.showForm();
                break;

            case STATE_SHOW_LOADING:
                view.showLoading();
                break;

            case STATE_SHOW_ERROR:
                view.showAuthError();
                break;
        }
    }
}
