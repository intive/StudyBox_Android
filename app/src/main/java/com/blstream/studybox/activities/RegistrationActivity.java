package com.blstream.studybox.activities;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.widget.TextView;

import com.blstream.studybox.ConnectionStatusReceiver;
import com.blstream.studybox.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistrationActivity extends AppCompatActivity {

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

    }

    @OnClick(R.id.link_cancel)
    public void OnCancelClick() {

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

}
