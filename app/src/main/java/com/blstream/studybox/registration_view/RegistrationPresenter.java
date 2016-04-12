package com.blstream.studybox.registration_view;

import com.blstream.studybox.login.CredentialValidator;
import com.blstream.studybox.login.ValidatorListener;
import com.blstream.studybox.model.AuthCredentials;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

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
                    getView().showEmptyEmailError();
                }
            }

            @Override
            public void onPasswordTooShort() {

            }

            @Override
            public void onEmailValidationFailure() {

            }

            @Override
            public void onPasswordValidationFailure() {

            }
        });

        validator.validate();
    }
}
