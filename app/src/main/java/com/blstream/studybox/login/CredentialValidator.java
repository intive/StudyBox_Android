package com.blstream.studybox.login;

import com.blstream.studybox.model.AuthCredentials;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CredentialValidator {

    private final static int MIN_PASSWORD_LENGTH = 8;
    private ValidatorListener listener;
    private AuthCredentials credentials;

    public CredentialValidator(AuthCredentials credentials, ValidatorListener listener) {
        this.credentials = credentials;
        this.listener = listener;
    }

    public void validate() {
        final String PASSWORD_PATTERN = "^(?=\\S+$).{" + MIN_PASSWORD_LENGTH + ",}$"; // min X characters, no whitespace
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(credentials.getPassword());

        boolean isPasswordValid = matcher.matches();
        boolean isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(credentials.getEmail()).matches();

        if (credentials.getPassword().isEmpty()) {
            listener.onPasswordFieldEmpty();
        } else if (credentials.getPassword().length() < MIN_PASSWORD_LENGTH) {
            listener.onPasswordTooShort();
        } else if (!isPasswordValid) {
            listener.onPasswordValidationFailure();
        }

        if (credentials.getEmail().isEmpty()) {
            listener.onEmailFieldEmpty();
        } else if (!isEmailValid) {
            listener.onEmailValidationFailure();
        }

        if (isEmailValid && isPasswordValid) {
            listener.onSuccess(credentials);
        }
    }
}
