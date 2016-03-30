package com.blstream.studybox.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.blstream.studybox.BaseActivity;
import com.blstream.studybox.ConnectionStatusReceiver;
import com.blstream.studybox.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity{
    @Bind(R.id.link_unlicensed_user)
    TextView unlicensedUserLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        unlicensedUserLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DecksActivity.class);
                startActivity(intent);
            }
        });
    }
}
