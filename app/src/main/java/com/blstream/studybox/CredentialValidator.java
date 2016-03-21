package com.blstream.studybox;

import com.blstream.studybox.model.AuthCredentials;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CredentialValidator {

    protected ValidatorListener listener;
    protected AuthCredentials credentials;

    public CredentialValidator(AuthCredentials credentials, ValidatorListener listener) {
        this.credentials = credentials;
        this.listener = listener;
    }

    public void validate() {
        final String PASSWORD_PATTERN = "^(?=\\S+$).{8,}$"; // min 8 characters, no whitespace
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(credentials.getPassword());

        boolean isPasswordValid = matcher.matches();
        boolean isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(credentials.getEmail()).matches();

        if (credentials.getEmail().isEmpty()) {
            listener.onEmailFieldEmpty();
        } else if (!isEmailValid) {
            listener.onEmailValidationFailure();
        }

        if (credentials.getPassword().isEmpty()) {
            listener.onPasswordFieldEmpty();
        } else if (!isPasswordValid) {
            listener.onPasswordValidationFailure();
        }

        if (isEmailValid && isPasswordValid) {
            listener.onSuccess(credentials);
        }
    }
}
