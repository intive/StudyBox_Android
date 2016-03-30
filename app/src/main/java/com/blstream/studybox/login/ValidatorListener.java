package com.blstream.studybox.login;

import com.blstream.studybox.model.AuthCredentials;

public interface ValidatorListener {

    void onSuccess(AuthCredentials credentials);

    void onEmailFieldEmpty();
    void onPasswordFieldEmpty();
    void onEmailValidationFailure();
    void onPasswordValidationFailure();
}
