package com.blstream.studybox.login_view;

import com.hannesdorfmann.mosby.mvp.MvpView;

public interface LoginView extends MvpView {

    void showLoginForm();
    void showAuthError();
    void showInvalidEmailError();
    void showInvalidPasswordError();
    void showLoading();
    void loginSuccessful();
}
