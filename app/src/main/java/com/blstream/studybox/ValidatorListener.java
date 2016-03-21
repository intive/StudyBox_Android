package com.blstream.studybox;

import com.blstream.studybox.model.AuthCredentials;

public interface ValidatorListener {

    void onSuccess(AuthCredentials credentials);

    void onEmailFieldEmpty();
    void onPasswordFieldEmpty();
    void onEmailValidationFailure();
    void onPasswordValidationFailure();
}
