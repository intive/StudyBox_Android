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
import android.widget.Toast;

import com.blstream.studybox.ConnectionStatusReceiver;
import com.blstream.studybox.R;
import com.blstream.studybox.login_view.LoginPresenter;
import com.blstream.studybox.login_view.LoginView;
import com.blstream.studybox.login_view.LoginViewState;
import com.blstream.studybox.model.AuthCredentials;
import com.hannesdorfmann.mosby.mvp.viewstate.MvpViewStateActivity;
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends MvpViewStateActivity<LoginView, LoginPresenter>
    implements LoginView {

    private ConnectionStatusReceiver connectionStatusReceiver;

    private final static float ENABLED_BUTTON_ALPHA = 1.0f;
    private final static float DISABLED_BUTTON_ALPHA = 0.5f;
    
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

        connectionStatusReceiver = new ConnectionStatusReceiver();
        setRetainInstance(true);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume(){
        super.onResume();
        registerReceiver(connectionStatusReceiver, ConnectionStatusReceiver.filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(connectionStatusReceiver);
    }

    @OnClick(R.id.btn_login)
    public void onLoginClick() {
        if (connectionStatusReceiver.isConnected()) {
            String email = emailInput.getText().toString();
            String password = passwordInput.getText().toString();
            presenter.validateCredential(new AuthCredentials(email, password));
        }
    }

    @OnClick(R.id.link_unlicensed_user)
    public void onUnlicensedClick() {
        Intent intent = new Intent(LoginActivity.this, DecksActivity.class);
        startActivity(intent);
    }

    @Override @NonNull
    public LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override @NonNull
    public ViewState<LoginView> createViewState() {
        return new LoginViewState();
    }

    @Override
    public void onNewViewStateInstance() {
        showLoginForm();
    }

    @Override
    public void showLoginForm() {
        LoginViewState vs = (LoginViewState) viewState;
        vs.setShowLoginForm();

        setLoginFormEnabled(true);
        authErrorView.setVisibility(View.GONE);
        loginProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showAuthError() {
        LoginViewState vs = (LoginViewState) viewState;
        vs.setShowAuthError();

        setLoginFormEnabled(true);
        authErrorView.setVisibility(View.VISIBLE);
        loginProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showNetworkError() {
        LoginViewState vs = (LoginViewState) viewState;
        vs.setShowLoginForm();

        Toast.makeText(LoginActivity.this, R.string.network_error, Toast.LENGTH_SHORT).show();

        setLoginFormEnabled(true);
        authErrorView.setVisibility(View.GONE);
        loginProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showUnexpectedError() {
        LoginViewState vs = (LoginViewState) viewState;
        vs.setShowLoginForm();

        Toast.makeText(LoginActivity.this, R.string.unexpected_error, Toast.LENGTH_SHORT).show();

        setLoginFormEnabled(true);
        authErrorView.setVisibility(View.GONE);
        loginProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showEmptyEmailError() {
        LoginViewState vs = (LoginViewState) viewState;
        vs.setShowLoginForm();

        setLoginFormEnabled(true);
        emailInput.setError(getString(R.string.empty_field));
        emailInput.requestFocus();
        authErrorView.setVisibility(View.GONE);
        loginProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showEmptyPasswordError() {
        LoginViewState vs = (LoginViewState) viewState;
        vs.setShowLoginForm();

        setLoginFormEnabled(true);
        passwordInput.setError(getString(R.string.empty_field));
        passwordInput.requestFocus();
        authErrorView.setVisibility(View.GONE);
        loginProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showInvalidEmailError() {
        LoginViewState vs = (LoginViewState) viewState;
        vs.setShowLoginForm();

        setLoginFormEnabled(true);
        emailInput.setError(getString(R.string.invalid_email));
        emailInput.requestFocus();
        authErrorView.setVisibility(View.GONE);
        loginProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showInvalidPasswordError() {
        LoginViewState vs = (LoginViewState) viewState;
        vs.setShowLoginForm();

        setLoginFormEnabled(true);
        passwordInput.setError(getString(R.string.invalid_password));
        passwordInput.requestFocus();
        authErrorView.setVisibility(View.GONE);
        loginProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        LoginViewState vs = (LoginViewState) viewState;
        vs.setShowLoading();

        setLoginFormEnabled(false);
        authErrorView.setVisibility(View.GONE);
        loginProgressBar.setVisibility(View.VISIBLE);
    }

    private void setLoginFormEnabled(boolean enabled) {
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
