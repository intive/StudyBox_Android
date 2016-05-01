package com.blstream.studybox.auth;

import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

public class BaseAuthViewState<V extends BaseAuthView> implements ViewState<V> {

    protected final int STATE_SHOW_FORM = 0;
    protected final int STATE_SHOW_LOADING = 1;
    protected final int STATE_SHOW_ERROR_AUTH = 2;
    protected final int STATE_SHOW_ERROR_NETWORK = 3;
    protected final int STATE_SHOW_ERROR_UNEXPECTED = 4;

    protected int state = STATE_SHOW_FORM;

    public void setShowForm() {
        state = STATE_SHOW_FORM;
    }

    public void setShowLoading() {
        state = STATE_SHOW_LOADING;
    }

    public void setShowAuthError() {
        state = STATE_SHOW_ERROR_AUTH;
    }

    public void setShowNetworkError() {
        state = STATE_SHOW_ERROR_NETWORK;
    }

    public void setShowUnexpectedError() {
        state = STATE_SHOW_ERROR_UNEXPECTED;
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

            case STATE_SHOW_ERROR_AUTH:
                view.showAuthError();
                break;

            case STATE_SHOW_ERROR_NETWORK:
                view.showNetworkError();
                break;

            case STATE_SHOW_ERROR_UNEXPECTED:
                view.showUnexpectedError();
                break;
        }
    }
}
