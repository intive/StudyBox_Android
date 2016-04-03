package com.blstream.studybox.activities;

import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;

import com.blstream.studybox.ConnectionStatusReceiver;
import com.blstream.studybox.Constants;
import com.blstream.studybox.R;
import com.blstream.studybox.components.DrawerAdapter;
import com.blstream.studybox.R;
import com.blstream.studybox.database.DataHelper;
import com.blstream.studybox.exam_view.DeckPagerAdapter;
import com.blstream.studybox.exam_view.fragment.AnswerFragment;
import com.blstream.studybox.exam_view.fragment.ResultDialogFragment;
import com.blstream.studybox.model.database.Deck;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ExamActivity extends AppCompatActivity implements AnswerFragment.OnMoveToNextCard, ResultDialogFragment.OnResultShow {

    private static final String TAG_RESULT = "result";
    private static final int PRE_LOAD_IMAGE_COUNT = 3;

    private ConnectionStatusReceiver connectionStatusReceiver = new ConnectionStatusReceiver();
    private DataHelper dataHelper = new DataHelper();

    @Bind(R.id.deckName)
    TextView deckName;

    @Bind(R.id.questionNo)
    TextView questionNo;

    @Bind(R.id.correctAnswers)
    TextView correctAnswers;

    @Bind(R.id.vpPager)
    ViewPager viewPager;

    @Bind(R.id.toolbar_exam)
    Toolbar toolbar;

    @Bind(R.id.nav_view_exam)
    NavigationView navigationView;

    @Bind(R.id.drawer_layout_exam)
    DrawerLayout drawerLayout;

    private DeckPagerAdapter adapterViewPager;
    private int cardCounter;
    private int correctAnswersCounter;
    private Integer noOfQuestions;
    private Deck deck;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        populateDeck();
        setVariables();
        initView();
    }

    private void initView() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        drawerAdapter = new DrawerAdapter(this, navigationView, drawerLayout, toolbar);
        drawerAdapter.attachDrawer();

        deckName.setText(deck.getDeckName());
        questionNo.setText(getString(R.string.question_no, cardCounter));
        correctAnswers.setText(getString(
                R.string.correct_answers, correctAnswersCounter, noOfQuestions));
        adapterViewPager =
                new DeckPagerAdapter(getSupportFragmentManager(), deck, PRE_LOAD_IMAGE_COUNT, this);
        viewPager.setAdapter(adapterViewPager);
    }

    private void setVariables() {
        noOfQuestions = deck.getNoOfQuestions();
        cardCounter = 1;
    }

    private void updateCard(boolean addCorrectAnswer) {
        updateCounters(addCorrectAnswer);
        setCard();
    }

    private void setCard() {
        if (cardCounter - 1  == noOfQuestions) {
            displayResult();
        } else {
            displayNextCard();
        }
    }

    private void displayNextCard() {
        viewPager.setCurrentItem(0, false);
        adapterViewPager.changeData();
        questionNo.setText(getString(R.string.question_no, cardCounter));
        correctAnswers.setText(getString(
                R.string.correct_answers, correctAnswersCounter, noOfQuestions));
    }

    private void displayResult(){
        ResultDialogFragment resultDialog = ResultDialogFragment.newInstance(
                correctAnswersCounter, deck.getNoOfQuestions());
        resultDialog.show(getSupportFragmentManager(), TAG_RESULT);
    }

    private void restartExam() {
        setInitialValues();
        adapterViewPager.onResultDisplay();
        setFirstCard();
    }

    private void updateCounters(boolean addCorrectAnswer){
        updateCorrectAnswersCounter(addCorrectAnswer);
        cardCounter++;
    }

    private void updateCorrectAnswersCounter(boolean addCorrectAnswer){
        if (addCorrectAnswer) {
            correctAnswersCounter++;
        }
    }

    private void setInitialValues() {
        cardCounter = 1;
        correctAnswersCounter = 0;
    }

    private void setFirstCard() {
        questionNo.setText(getString(R.string.question_no, 1));
        correctAnswers.setText(getString(
                R.string.correct_answers, correctAnswersCounter, noOfQuestions));
        viewPager.setCurrentItem(0, false);
    }

    @Override
    public void onMoveToNextCard(boolean addCorrectAnswer) {
        updateCard(addCorrectAnswer);
    }

    @Override
    public void onResultShow() {
        restartExam();
    }

    private void populateDeck() {
        Bundle data = getIntent().getExtras();
        if (data == null) {
            return;
        }

        deck = data.getParcelable(Constants.DECK_DATA_KEY);
        if (deck == null) {
            return;
        }

        deck.setCardsList(dataHelper.getAllCards(deck.getDeckNo()));
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
}
