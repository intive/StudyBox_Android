package com.blstream.studybox.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.blstream.studybox.Constants;
import com.blstream.studybox.components.DrawerAdapter;
import com.blstream.studybox.R;
import com.blstream.studybox.exam_view.DeckPagerAdapter;
import com.blstream.studybox.exam_view.fragment.AnswerFragment;
import com.blstream.studybox.exam_view.fragment.ResultDialogFragment;
import com.blstream.studybox.model.Card;
import com.blstream.studybox.model.Deck;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ExamActivity extends AppCompatActivity implements AnswerFragment.OnMoveToNextCard, ResultDialogFragment.OnResultShow {

    @Bind(R.id.deckName)
    public TextView deckName;

    @Bind(R.id.questionNo)
    public TextView questionNo;

    @Bind(R.id.correctAnswers)
    public TextView correctAnswers;

    @Bind(R.id.vpPager)
    public ViewPager viewPager;

    @Bind(R.id.toolbar_exam)
    Toolbar toolbar;

    @Bind(R.id.nav_view_exam)
    NavigationView navigationView;

    @Bind(R.id.drawer_layout_exam)
    DrawerLayout drawerLayout;

    private DeckPagerAdapter adapterViewPager;
    private int cardCounter;
    private int correctAnswersCounter;
 //   private int totalPages;
    private Integer noOfQuestions;
    DrawerAdapter drawerAdapter;

    //------For testing------
    private final List<Card> questions = new ArrayList<>();

    private Deck deck;
    //------For testing------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        Bundle data = getIntent().getExtras();
        deck = data.getParcelable(Constants.DECK_DATA_KEY);

        //------For testing------
        populateDeck();
        //------For testing------

        setVariables();
        initView();
    }

    private void initView() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        Context context = getApplicationContext();      //only For testing
        drawerAdapter = new DrawerAdapter(navigationView, drawerLayout, toolbar, context);
        drawerAdapter.attachDrawer();

        deckName.setText(deck.getDeckName());
        questionNo.setText(getString(R.string.question_no, cardCounter));
        correctAnswers.setText(getString(
                R.string.correct_answers, correctAnswersCounter, noOfQuestions));
        adapterViewPager =
                new DeckPagerAdapter(getSupportFragmentManager(), deck, 4, this);
        viewPager.setAdapter(adapterViewPager);
    }

    private void setVariables(){
        noOfQuestions = deck.getNoOfQuestions();
        cardCounter = 1;
    }

    private void updateCard(boolean addCorrectAnswer){
        updateCounters(addCorrectAnswer);
        setCard();
    }

    private void setCard(){
        if(cardCounter - 1  == noOfQuestions)
            displayResult();
        else
            displayNextCard();
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
        resultDialog.show(getSupportFragmentManager(), "result");
    }

    private void restartExam(){
        setInitialValues();
        adapterViewPager.onResultDisplay();
        setFirstCard();
    }

    private void updateCounters(boolean addCorrectAnswer){
        updateCorrectAnswersCounter(addCorrectAnswer);
        cardCounter++;
    }

    private void updateCorrectAnswersCounter(boolean addCorrectAnswer){
        if(addCorrectAnswer)
            correctAnswersCounter++;
    }

    private void setInitialValues(){
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
    public void onResultShow(){
        restartExam();
    }

    private void populateDeck() {

        for (Card card : deck.getCards()) {
            questions.add(card);
        }
    }
}
