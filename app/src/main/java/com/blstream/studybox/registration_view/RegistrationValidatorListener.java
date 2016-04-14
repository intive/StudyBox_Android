package com.blstream.studybox.registration_view;

import com.blstream.studybox.login.ValidatorListener;
import com.blstream.studybox.model.AuthCredentials;

/**
 * Created by Marek Macko on 14.04.2016.
 */
public interface RegistrationValidatorListener extends ValidatorListener {

    @Override
    void onSuccess(AuthCredentials credentials);
    void onPasswordsInconsistent();
}
