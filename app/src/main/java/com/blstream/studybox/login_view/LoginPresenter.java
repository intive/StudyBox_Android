package com.blstream.studybox.login_view;

import com.blstream.studybox.auth.BaseAuthPresenter;
import com.blstream.studybox.login.CredentialValidator;
import com.blstream.studybox.login.LoginManager;
import com.blstream.studybox.login.ValidatorListener;
import com.blstream.studybox.api.AuthRequestInterceptor;
import com.blstream.studybox.api.RequestCallback;
import com.blstream.studybox.api.RequestListener;
import com.blstream.studybox.api.RestClientManager;
import com.blstream.studybox.model.AuthCredentials;

import retrofit.RetrofitError;

public class LoginPresenter extends BaseAuthPresenter<LoginView> {

    public void validateCredential(AuthCredentials credentials) {

        if (isViewAttached()) {
            getView().showLoading();
        }

        CredentialValidator validator = new CredentialValidator(credentials, new ValidatorListener() {
            @Override
            public void onSuccess(AuthCredentials credentials) {
                authenticate(credentials);
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

    protected void authenticate(final AuthCredentials credentials) {
        RestClientManager.authenticate(new AuthRequestInterceptor(credentials),
                new RequestCallback<>(new RequestListener<AuthCredentials>() {
                    @Override
                    public void onSuccess(AuthCredentials response) {
                        if (isViewAttached()) {
                            getView().loginSuccessful();

                            response.setPassword(credentials.getPassword());
                            LoginManager login = new LoginManager(getView().getContext());
                            login.saveUser(response);
                        }
                    }

                    @Override
                    public void onFailure(RetrofitError error) {
                        if (error.getKind().equals(RetrofitError.Kind.NETWORK)) {
                            if (isViewAttached()) {
                                getView().showNetworkError();
                            }
                        } else if (error.getKind().equals(RetrofitError.Kind.HTTP)){
                            if (isViewAttached()) {
                                getView().showAuthError();
                            }
                        } else {
                            if (isViewAttached()) {
                                getView().showUnexpectedError();
                            }
                        }
                    }
                })
        );
    }
}
