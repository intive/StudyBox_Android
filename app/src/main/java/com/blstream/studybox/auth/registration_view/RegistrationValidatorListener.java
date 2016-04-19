package com.blstream.studybox.auth.registration_view;

import com.blstream.studybox.auth.login.ValidatorListener;

/**
 * Created by Marek Macko on 14.04.2016.
 */
public interface RegistrationValidatorListener extends ValidatorListener {

    void onPasswordsInconsistent();
}
