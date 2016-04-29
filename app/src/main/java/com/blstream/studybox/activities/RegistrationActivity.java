package com.blstream.studybox.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blstream.studybox.R;
import com.blstream.studybox.auth.BaseAuthViewState;
import com.blstream.studybox.auth.registration_view.RegistrationPresenter;
import com.blstream.studybox.auth.registration_view.RegistrationView;
import com.blstream.studybox.model.AuthCredentials;
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class RegistrationActivity
        extends BaseAuthActivity<RegistrationView, RegistrationPresenter>
        implements RegistrationView {

    @Bind(R.id.text_view_failure)
    TextView textViewFailure;

    @Bind(R.id.input_email)
    TextInputEditText inputEmail;

    @Bind(R.id.input_password)
    TextInputEditText inputPassword;

    @Bind(R.id.input_repeat_password)
    TextInputEditText inputRepeatPassword;

    @Bind(R.id.progress_bar_sign_up)
    ProgressBar signUpProgressBar;

    @Bind(R.id.btn_sign_up)
    AppCompatButton buttonSignUp;

    @Bind(R.id.link_cancel)
    TextView linkCancel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_sign_up)
    public void onSignUpClick() {
        if (connectionStatusReceiver.isConnected()) {
            String email = inputEmail.getText().toString().trim();
            String password = inputPassword.getText().toString();
            String repeatPassword = inputRepeatPassword.getText().toString();

            presenter.validateCredential(new AuthCredentials(email, password, repeatPassword));
        }
    }

    @OnClick(R.id.link_cancel)
    public void onCancelClick() {
        finish();
    }

    @NonNull
    @Override
    public RegistrationPresenter createPresenter() {
        return new RegistrationPresenter();
    }

    @Override
    public void onNewViewStateInstance() {
        showForm();
    }

    @Override
    public void showEmptyRepeatPasswordError() {
        setFieldError(inputRepeatPassword, getString(R.string.set_obligatory_field));
    }

    @Override
    public void showPasswordInconsistent() {
        setFieldError(inputRepeatPassword, getString(R.string.inconsistent_passwords));
    }

    @Override
    public void showForm() {
        BaseAuthViewState vs = (BaseAuthViewState<RegistrationView>) viewState;
        vs.setShowForm();

        setFormEnabled(true);
        textViewFailure.setVisibility(View.GONE);
        signUpProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showAuthError() {
        setAuthError(getString(R.string.account_exist));
    }

    @Override
    public void showNetworkError() {
        setNetworkError(getString(R.string.network_error));
    }

    @Override
    public void showUnexpectedError() {
        setUnexpectedError(getString(R.string.unexpected_error));
    }

    @Override
    public void showEmptyEmailError() {
        setFieldError(inputEmail, getString(R.string.set_obligatory_field));
    }

    @Override
    public void showEmptyPasswordError() {
        setFieldError(inputPassword, getString(R.string.set_obligatory_field));
    }

    @Override
    public void showInvalidEmailError() {
        setFieldError(inputEmail, getString(R.string.invalid_email));
    }

    @Override
    public void showInvalidPasswordError() {
        setFieldError(inputPassword, getString(R.string.invalid_password_whitespaces));
    }

    @Override
    public void showTooShortPasswordError() {
        setFieldError(inputPassword, getString(R.string.too_short_password));
    }

    @Override
    public void showLoading() {
        BaseAuthViewState vs = (BaseAuthViewState<RegistrationView>) viewState;
        vs.setShowLoading();

        setFormEnabled(false);
        textViewFailure.setVisibility(View.GONE);
        signUpProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void loginSuccessful() {
        Intent intent = new Intent(RegistrationActivity.this, DecksActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public Context getContext() {
        return RegistrationActivity.this;
    }

    @NonNull
    @Override
    public ViewState<RegistrationView> createViewState() {
        return new BaseAuthViewState<>();
    }

    protected void setAuthError(String message) {
        BaseAuthViewState vs = (BaseAuthViewState<RegistrationView>) viewState;
        vs.setShowAuthError();

        setError(message);
    }

    protected void setNetworkError(String message) {
        BaseAuthViewState vs = (BaseAuthViewState<RegistrationView>) viewState;
        vs.setShowNetworkError();

        setError(message);
    }

    protected void setUnexpectedError(String message) {
        BaseAuthViewState vs = (BaseAuthViewState<RegistrationView>) viewState;
        vs.setShowUnexpectedError();

        setError(message);
    }

    @Override
    protected void setError(String message) {
        setFormEnabled(true);
        textViewFailure.setText(message);
        textViewFailure.setVisibility(View.VISIBLE);
        signUpProgressBar.setVisibility(View.GONE);
    }

    protected void setFieldError(TextInputEditText field, String message) {
        BaseAuthViewState vs = (BaseAuthViewState<RegistrationView>) viewState;
        vs.setShowForm();

        setFormEnabled(true);
        field.setError(message);
        field.requestFocus();
        textViewFailure.setVisibility(View.GONE);
        signUpProgressBar.setVisibility(View.GONE);
    }

    @Override
    protected void setFormEnabled(boolean enabled) {
        inputEmail.setEnabled(enabled);
        inputPassword.setEnabled(enabled);
        inputRepeatPassword.setEnabled(enabled);
        buttonSignUp.setEnabled(enabled);
        linkCancel.setEnabled(enabled);

        if (enabled) {
            buttonSignUp.setAlpha(ENABLED_BUTTON_ALPHA);
        } else {
            buttonSignUp.setAlpha(DISABLED_BUTTON_ALPHA);
        }
    }
}
