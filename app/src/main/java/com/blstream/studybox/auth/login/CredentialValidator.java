package com.blstream.studybox.auth.login;

import com.blstream.studybox.model.AuthCredentials;
import com.blstream.studybox.auth.registration_view.RegistrationValidatorListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CredentialValidator {

    private final static int MIN_PASSWORD_LENGTH = 8;
    private ValidatorListener listener;
    private AuthCredentials credentials;
    private boolean isPasswordValid;
    private boolean isRepeatPasswordValid;
    private boolean isEmailValid;

    public CredentialValidator(AuthCredentials credentials, ValidatorListener listener) {
        this.credentials = credentials;
        this.listener = listener;
    }

    public void validate() {
        final String PASSWORD_PATTERN = "^(?=\\S+$).{" + MIN_PASSWORD_LENGTH + ",}$"; // min X characters, no whitespace
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(credentials.getPassword());

        isPasswordValid = matcher.matches();
        isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(credentials.getEmail()).matches();

        validateRepeatPassword();
        validatePassword();
        validateMail();

        isAllValid();
    }

    private void validatePassword() {
        if (credentials.getPassword().isEmpty()) {
            listener.onPasswordFieldEmpty();
        } else if (credentials.getPassword().length() < MIN_PASSWORD_LENGTH) {
            listener.onPasswordTooShort();
        } else if (!isPasswordValid) {
            listener.onPasswordValidationFailure();
        }
    }

    private void validateMail() {
        if (credentials.getEmail().isEmpty()) {
            listener.onEmailFieldEmpty();
        } else if (!isEmailValid) {
            listener.onEmailValidationFailure();
        }
    }

    private void validateRepeatPassword() {
        if (listener instanceof RegistrationValidatorListener) {
            if (credentials.getRepeatPassword().isEmpty()) {
                ((RegistrationValidatorListener) listener).onShowEmptyRepeatPasswordError();
            } else  if (!credentials.getPassword().equals(credentials.getRepeatPassword())) {
                ((RegistrationValidatorListener) listener).onPasswordsInconsistent();
            } else {
                isRepeatPasswordValid = true;
            }
        }
    }

    private void isAllValid() {
        if (listener instanceof RegistrationValidatorListener) {
            if (isEmailValid && isPasswordValid && isRepeatPasswordValid) {
                listener.onSuccess(credentials);
            }
        } else {
            if (isEmailValid && isPasswordValid) {
                listener.onSuccess(credentials);
            }
        }
    }
}
