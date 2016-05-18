package com.blstream.studybox.auth;

import com.hannesdorfmann.mosby.mvp.MvpView;

public interface BaseAuthView extends MvpView {

    void showForm();
    void showAuthError();
    void showNetworkError();
    void showUnexpectedError();
    void showEmptyEmailError();
    void showEmptyPasswordError();
    void showInvalidEmailError();
    void showInvalidPasswordError();
    void showTooShortPasswordError();
    void showLoading();
}
