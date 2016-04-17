package com.blstream.studybox.registration_view;

import com.blstream.studybox.login_view.LoginView;
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

/**
 * Created by Marek Macko on 12.04.2016.
 */
public class RegistrationViewState implements ViewState<RegistrationView> {

    private final int STATE_SHOW_LOGIN_FORM = 0;
    private final int STATE_SHOW_LOADING = 1;
    private final int STATE_SHOW_ERROR = 2;

    private int state = STATE_SHOW_LOGIN_FORM;

    public void setShowLoginForm() {
        state = STATE_SHOW_LOGIN_FORM;
    }

    public void setShowLoading() {
        state = STATE_SHOW_LOADING;
    }

    public void setShowError() {
        state = STATE_SHOW_ERROR;
    }

    @Override
    public void apply(RegistrationView view, boolean retained) {

        switch (state) {
            case STATE_SHOW_LOGIN_FORM:
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
