package com.blstream.studybox.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.blstream.studybox.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Bartosz Kozajda on 16.03.2016.
 */
public class LoginActivity extends AppCompatActivity {
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
