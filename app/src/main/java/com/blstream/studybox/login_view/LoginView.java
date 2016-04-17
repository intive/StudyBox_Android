package com.blstream.studybox.login_view;

import android.content.Context;

import com.hannesdorfmann.mosby.mvp.MvpView;

public interface LoginView extends MvpView {

    void showLoginForm();
    void showAuthError();
    void showNetworkError();
    void showUnexpectedError();
    void showEmptyEmailError();
    void showEmptyPasswordError();
    void showInvalidEmailError();
    void showInvalidPasswordError();
    void showTooShortPasswordError();
    void showLoading();
    void loginSuccessful();
    Context getContext();
}
