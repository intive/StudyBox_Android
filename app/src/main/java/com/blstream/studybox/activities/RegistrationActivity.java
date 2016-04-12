package com.blstream.studybox.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.TextView;

import com.blstream.studybox.ConnectionStatusReceiver;
import com.blstream.studybox.R;
import com.blstream.studybox.login_view.LoginViewState;
import com.blstream.studybox.model.AuthCredentials;
import com.blstream.studybox.registration_view.RegistrationPresenter;
import com.blstream.studybox.registration_view.RegistrationView;
import com.blstream.studybox.registration_view.RegistrationViewState;
import com.hannesdorfmann.mosby.mvp.viewstate.MvpViewStateActivity;
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistrationActivity
        extends MvpViewStateActivity<RegistrationView, RegistrationPresenter>
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
        setFieldError(inputPassword, getString(R.string.empty_field));
    }

    @Override
    public void showEmptyPasswordError() {
        setFieldError(inputEmail, getString(R.string.empty_field));
    }

    @Override
    public void showInvalidEmailError() {

    }

    @Override
    public void showInvalidPasswordError() {

    }

    @Override
    public void showTooShortPasswordError() {

    }

    @Override
    public void showLoading() {
        RegistrationViewState vs = (RegistrationViewState) viewState;
        vs.setShowLoading();
    }

    @Override
    public void loginSuccessful() {

    }

    @Override
    public Context getContext() {
        return RegistrationActivity.this;
    }

    @NonNull
    @Override
    public ViewState<RegistrationView> createViewState() {
        return new RegistrationViewState();
    }

    private void setFieldError(TextInputEditText field, String message) {
        RegistrationViewState vs = (RegistrationViewState) viewState;
        vs.setShowLoginForm();

        field.setError(message);
        field.requestFocus();
    }
}
