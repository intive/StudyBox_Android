package com.blstream.studybox.activities;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blstream.studybox.ConnectionStatusReceiver;
import com.blstream.studybox.Constants;
import com.blstream.studybox.R;
import com.blstream.studybox.login_view.LoginPresenter;
import com.blstream.studybox.login_view.LoginView;
import com.blstream.studybox.login_view.LoginViewState;
import com.blstream.studybox.model.AuthCredentials;
import com.hannesdorfmann.mosby.mvp.viewstate.MvpViewStateActivity;
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

import butterknife.Bind;
import butterknife.ButterKnife;


public class LoginActivity extends MvpViewStateActivity<LoginView, LoginPresenter>
    implements LoginView {
    public ConnectionStatusReceiver connectionStatusReceiver = new ConnectionStatusReceiver();
    
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

        setRetainInstance(true);
        ButterKnife.bind(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailInput.getText().toString();
                String password = passwordInput.getText().toString();

                presenter.validateCredential(new AuthCredentials(email, password));
            }
        });

        unlicensedUserLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DecksActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        IntentFilter filter = new IntentFilter(Constants.ACTION);
        registerReceiver(connectionStatusReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(connectionStatusReceiver);
    }

    @Override
    public LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
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
            loginButton.setAlpha(1f);
        } else {
            loginButton.setAlpha(0.5f);
        }
    }

    @Override
    public void loginSuccessful() {
        Intent intent = new Intent(getApplicationContext(), DecksActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public Context getContext() {
        return LoginActivity.this;
    }
}
