package com.blstream.studybox;

import com.blstream.studybox.model.AuthCredentials;

public interface ValidatorListener {

    void onSuccess(AuthCredentials credentials);

    void onEmailValidationFailure();
    void onPasswordValidationFailure();
}
