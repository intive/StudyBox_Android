package com.blstream.studybox.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blstream.studybox.ConnectionStatusReceiver;
import com.blstream.studybox.R;
import com.blstream.studybox.login_view.LoginView;
import com.blstream.studybox.login_view.LoginViewState;
import com.blstream.studybox.model.AuthCredentials;
import com.blstream.studybox.registration_view.RegistrationPresenter;
import com.blstream.studybox.registration_view.RegistrationView;
import com.hannesdorfmann.mosby.mvp.viewstate.MvpViewStateActivity;
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class RegistrationActivity
        extends MvpViewStateActivity<LoginView, RegistrationPresenter>
        implements RegistrationView {

    private ConnectionStatusReceiver connectionStatusReceiver;

    @Bind(R.id.text_view_failure)
    TextView textViewFailure;

    @Bind(R.id.input_layout_email)
    TextInputLayout inputLayoutEmail;

    @Bind(R.id.input_layout_password)
    TextInputLayout inputLayoutPassword;

    @Bind(R.id.input_layout_repeat_password)
    TextInputLayout inputLayoutRepeatPassword;

    @Bind(R.id.input_email)
    TextInputEditText inputEmail;

    @Bind(R.id.input_password)
    TextInputEditText inputPassword;

    @Bind(R.id.input_repeat_password)
    TextInputEditText inputRepeatPassword;

    @Bind(R.id.text_view_failure)
    TextView viewError;

    @Bind(R.id.progress_bar_sign_up)
    ProgressBar signUpProgressBar;

    @Bind(R.id.btn_sign_up)
    AppCompatButton buttonSignUp;

    @Bind(R.id.link_cancel)
    TextView linkCancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        connectionStatusReceiver = new ConnectionStatusReceiver();

        ButterKnife.bind(this);

    }

    @OnClick(R.id.btn_sign_up)
    public void OnSignUpClick() {
        if (connectionStatusReceiver.isConnected()) {
            String email = inputEmail.getText().toString().trim();
            String password = inputPassword.getText().toString();
            presenter.validateCredential(new AuthCredentials(email, password));
        }
    }

    @OnClick(R.id.link_cancel)
    public void OnCancelClick() {

    }

    @Override
    protected void onResume(){
        super.onResume();
        registerReceiver(connectionStatusReceiver, ConnectionStatusReceiver.filter);
    }

    @NonNull
    @Override
    public RegistrationPresenter createPresenter() {
        return new RegistrationPresenter();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(connectionStatusReceiver);
    }

    @Override
    public void onNewViewStateInstance() {

    }

    @Override
    public void showPasswordInconsistent() {

    }

    @Override
    public void showLoginForm() {

    }

    @Override
    public void showAuthError() {

    }

    @Override
    public void showNetworkError() {

    }

    @Override
    public void showUnexpectedError() {

    }

    @Override
    public void showEmptyEmailError() {
        setFieldError(inputEmail, getString(R.string.empty_field));
    }

    @Override
    public void showEmptyPasswordError() {
        setFieldError(inputPassword, getString(R.string.empty_field));
    }

    @Override
    public void showInvalidEmailError() {
        setFieldError(inputEmail, getString(R.string.invalid_email));
    }

    @Override
    public void showInvalidPasswordError() {

    }

    @Override
    public void showTooShortPasswordError() {
        setFieldError(inputPassword, getString(R.string.too_short_password));
    }

    @Override
    public void showLoading() {
        LoginViewState vs = (LoginViewState) viewState;
        vs.setShowLoading();
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
    public ViewState<LoginView> createViewState() {
        return new LoginViewState();
    }

    private void setFieldError(TextInputEditText field, String message) {
        LoginViewState vs = (LoginViewState) viewState;
        vs.setShowLoginForm();

        field.setError(message);
        field.requestFocus();

    }

    /**
     * Applies custom font to every activity that overrides this method
     */
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
