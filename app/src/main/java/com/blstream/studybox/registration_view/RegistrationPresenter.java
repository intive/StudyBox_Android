package com.blstream.studybox.registration_view;

import android.util.Log;

import com.blstream.studybox.api.RequestCallback;
import com.blstream.studybox.api.RequestListener;
import com.blstream.studybox.api.RestClientManager;
import com.blstream.studybox.login.CredentialValidator;
import com.blstream.studybox.login.ValidatorListener;
import com.blstream.studybox.model.AuthCredentials;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import retrofit.RetrofitError;

/**
 * Created by Marek Macko on 12.04.2016.
 */
public class RegistrationPresenter extends MvpBasePresenter<RegistrationView> {

    public void validateCredential(AuthCredentials credentials) {

        if (isViewAttached()) {
            getView().showLoading();
        }

        CredentialValidator validator = new CredentialValidator(credentials, new ValidatorListener() {
            @Override
            public void onSuccess(AuthCredentials credentials) {
                signUp(credentials);
            }

            @Override
            public void onEmailFieldEmpty() {
                if (isViewAttached()) {
                    getView().showEmptyEmailError();
                }
            }

            @Override
            public void onPasswordFieldEmpty() {
                if (isViewAttached()) {
                    getView().showEmptyPasswordError();
                }
            }

            @Override
            public void onPasswordTooShort() {
                if (isViewAttached()) {
                    getView().showTooShortPasswordError();
                }
            }

            @Override
            public void onEmailValidationFailure() {
                if (isViewAttached()) {
                    getView().showInvalidEmailError();
                }
            }

            @Override
            public void onPasswordValidationFailure() {
                if (isViewAttached()) {
                    getView().showInvalidPasswordError();
                }
            }
        });

        validator.validate();
    }

    private void signUp(final AuthCredentials credentials) {
        RestClientManager.signUp(credentials,
                new RequestCallback<>(new RequestListener<AuthCredentials>() {
                    @Override
                    public void onSuccess(AuthCredentials response) {
                        Log.e("SIGNUP", "user registered :)");
                    }

                    @Override
                    public void onFailure(RetrofitError error) {
                        Log.e("SIGNUP", "failure :(");
                    }
                }));
    }
}
