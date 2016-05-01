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
import com.blstream.studybox.base.BaseAuthActivity;
import com.blstream.studybox.auth.BaseAuthViewState;
import com.blstream.studybox.auth.login_view.LoginPresenter;
import com.blstream.studybox.auth.login_view.LoginView;
import com.blstream.studybox.model.AuthCredentials;
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends BaseAuthActivity<LoginView, LoginPresenter>
    implements LoginView {
    
    @Bind(R.id.input_email)
    TextInputEditText emailInput;

    @Bind(R.id.input_password)
    TextInputEditText passwordInput;

    @Bind(R.id.btn_login)
    AppCompatButton loginButton;

    @Bind(R.id.view_auth_error)
    TextView authErrorView;

    @Bind(R.id.progress_bar_login)
    ProgressBar loginProgressBar;

    @Bind(R.id.link_unlicensed_user)
    TextView unlicensedUserLink;

    @Bind(R.id.link_sign_up)
    TextView signUpLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setRetainInstance(true);
    }

    @OnClick(R.id.btn_login)
    public void onLoginClick() {
        if (connectionStatusReceiver.isConnected()) {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString();
            presenter.validateCredential(new AuthCredentials(email, password));
        }
    }

    @OnClick(R.id.link_unlicensed_user)
    public void onUnlicensedClick() {
        Intent intent = new Intent(LoginActivity.this, DecksActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.link_sign_up)
    public void onSignUpLinkClick(){
        Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
        startActivity(intent);
    }

    @Override @NonNull
    public LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override @NonNull
    public ViewState<LoginView> createViewState() {
        return new BaseAuthViewState<>();
    }

    @Override
    public void onNewViewStateInstance() {
        showForm();
    }

    @Override
    public void showForm() {
        BaseAuthViewState vs = (BaseAuthViewState<LoginView>) viewState;
        vs.setShowForm();

        setFormEnabled(true);
        authErrorView.setVisibility(View.GONE);
        loginProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showAuthError() {
        setAuthError(getString(R.string.auth_error));
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
        setFieldError(emailInput, getString(R.string.empty_field));
    }

    @Override
    public void showEmptyPasswordError() {
        setFieldError(passwordInput, getString(R.string.empty_field));
    }

    @Override
    public void showInvalidEmailError() {
        setFieldError(emailInput, getString(R.string.invalid_email));
    }

    @Override
    public void showInvalidPasswordError() {
        setFieldError(passwordInput, getString(R.string.invalid_password));
    }

    @Override
    public void showTooShortPasswordError() {
        setFieldError(passwordInput, getString(R.string.too_short_password));
    }

    @Override
    public void showLoading() {
        BaseAuthViewState vs = (BaseAuthViewState<LoginView>) viewState;
        vs.setShowLoading();

        setFormEnabled(false);
        authErrorView.setVisibility(View.GONE);
        loginProgressBar.setVisibility(View.VISIBLE);
    }

    protected void setAuthError(String message){
        BaseAuthViewState vs = (BaseAuthViewState<LoginView>) viewState;
        vs.setShowAuthError();

        setError(message);
    }

    protected void setNetworkError(String message){
        BaseAuthViewState vs = (BaseAuthViewState<LoginView>) viewState;
        vs.setShowNetworkError();

        setError(message);
    }

    protected void setUnexpectedError(String message){
        BaseAuthViewState vs = (BaseAuthViewState<LoginView>) viewState;
        vs.setShowUnexpectedError();

        setError(message);
    }

    @Override
    protected void setError(String message) {
        setFormEnabled(true);
        authErrorView.setText(message);
        authErrorView.setVisibility(View.VISIBLE);
        loginProgressBar.setVisibility(View.GONE);
    }

    @Override
    protected void setFieldError(TextInputEditText field, String message) {
        BaseAuthViewState vs = (BaseAuthViewState<LoginView>) viewState;
        vs.setShowForm();

        setFormEnabled(true);
        field.setError(message);
        field.requestFocus();
        authErrorView.setVisibility(View.GONE);
        loginProgressBar.setVisibility(View.GONE);
    }

    @Override
    protected void setFormEnabled(boolean enabled) {
        emailInput.setEnabled(enabled);
        passwordInput.setEnabled(enabled);
        loginButton.setEnabled(enabled);
        unlicensedUserLink.setEnabled(enabled);
        signUpLink.setEnabled(enabled);

        if (enabled) {
            loginButton.setAlpha(ENABLED_BUTTON_ALPHA);
        } else {
            loginButton.setAlpha(DISABLED_BUTTON_ALPHA);
        }
    }

    @Override
    public void loginSuccessful() {
        Intent intent = new Intent(LoginActivity.this, DecksActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public Context getContext() {
        return LoginActivity.this;
    }
}
