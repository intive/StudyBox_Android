package com.blstream.studybox.login_view;

import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

public class LoginViewState implements ViewState<LoginView> {

    private final int STATE_SHOW_LOGIN_FORM = 0;
    private final int STATE_SHOW_LOADING = 1;
    private final int STATE_SHOW_AUTH_ERROR = 2;

    private int state = STATE_SHOW_LOGIN_FORM;

    public void setShowLoginForm() {
        state = STATE_SHOW_LOGIN_FORM;
    }

    public void setShowLoading() {
        state = STATE_SHOW_LOADING;
    }

    public void setShowAuthError() {
        state = STATE_SHOW_AUTH_ERROR;
    }

    @Override
    public void apply(LoginView view, boolean retained) {

        switch (state) {
            case STATE_SHOW_LOGIN_FORM:
                view.showLoginForm();
                break;

            case STATE_SHOW_LOADING:
                view.showLoading();
                break;

            case STATE_SHOW_AUTH_ERROR:
                view.showAuthError();
                break;
        }
    }
}
