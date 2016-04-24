package com.blstream.studybox.activities;

import android.os.Bundle;
import android.view.View;

import com.blstream.studybox.R;
import com.blstream.studybox.activities.base.BaseBasicActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class EmptyDeckActivity extends BaseBasicActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_deck);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.add_flashcards_button)
    public void addFlashcards(View view) {
        //we'll navigate to class responsible for adding flashcards from here
        finish();
    }

    @OnClick(R.id.my_decks_button)
    public void backToMyDecks(View view) {
        finish();
    }
}
