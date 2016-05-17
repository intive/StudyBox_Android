package com.blstream.studybox.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.blstream.studybox.R;
import com.blstream.studybox.base.BaseAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class EmptyDeckActivity extends BaseAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_deck);
        ButterKnife.bind(this);
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
