package com.blstream.studybox.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.blstream.studybox.R;
import com.blstream.studybox.auth.login.LoginManager;
import com.blstream.studybox.base.BaseAppCompatActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EmptyDeckActivity extends BaseAppCompatActivity {

    private LoginManager loginManager = new LoginManager();

    @Bind(R.id.my_decks_button)
    Button myDecks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_deck);
        ButterKnife.bind(this);
        setButtonText();

    }

    private void setButtonText() {
        if (loginManager.isUserLoggedIn()) {
            myDecks.setText(R.string.my_decks);
        } else {
            myDecks.setText(R.string.decks);
        }
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, EmptyDeckActivity.class);
        context.startActivity(intent);
    }

    @OnClick(R.id.my_decks_button)
    public void backToMyDecks(View view) {
        finish();
    }
}
