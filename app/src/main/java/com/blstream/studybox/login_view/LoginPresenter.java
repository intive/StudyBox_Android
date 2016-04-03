package com.blstream.studybox.login_view;

import com.blstream.studybox.login.CredentialValidator;
import com.blstream.studybox.login.LoginUtils;
import com.blstream.studybox.login.ValidatorListener;
import com.blstream.studybox.api.AuthRequestInterceptor;
import com.blstream.studybox.api.RequestCallback;
import com.blstream.studybox.api.RequestListener;
import com.blstream.studybox.api.RestClientManager;
import com.blstream.studybox.model.AuthCredentials;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginPresenter extends MvpBasePresenter<LoginView> {

    private static final String AUTH_URL = "http://gibkiezuczki.azurewebsites.net/";

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
        RestClientManager.authenticate(AUTH_URL, new AuthRequestInterceptor(credentials),
                new RequestCallback<>(new RequestListener<Response>() {
                    @Override
                    public void onSuccess(Response response) {
                        if (isViewAttached()) {
                            getView().loginSuccessful();
                            LoginUtils.saveUser(credentials, getView().getContext());
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
