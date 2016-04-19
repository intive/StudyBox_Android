package com.blstream.studybox.auth.login;

import com.blstream.studybox.model.AuthCredentials;

public interface ValidatorListener {

    void onSuccess(AuthCredentials credentials);

    void onEmailFieldEmpty();
    void onPasswordFieldEmpty();
    void onPasswordTooShort();
    void onEmailValidationFailure();
    void onPasswordValidationFailure();
}
