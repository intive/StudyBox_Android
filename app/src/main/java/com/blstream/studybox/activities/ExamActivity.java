package com.blstream.studybox.activities;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blstream.studybox.ConnectionStatusReceiver;
import com.blstream.studybox.R;
import com.blstream.studybox.api.RequestListener;
import com.blstream.studybox.components.DrawerAdapter;
import com.blstream.studybox.database.DataHelper;
import com.blstream.studybox.exam_view.DeckPagerAdapter;
import com.blstream.studybox.exam_view.fragment.AnswerFragment;
import com.blstream.studybox.exam_view.fragment.ResultDialogFragment;
import com.blstream.studybox.model.database.Card;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.RetrofitError;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ExamActivity extends AppCompatActivity implements AnswerFragment.OnMoveToNextCard, ResultDialogFragment.OnResultShow, RequestListener<String>, ResultDialogFragment.MyDialogCloseListener {

    private static final String TAG_RESULT = "result";
    private static final int PRE_LOAD_IMAGE_COUNT = 3;
    private static final int ANIMATION_DURATION = 1000;
    private static final int TRANSITION_DURATION = 500;

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

    @Bind(R.id.content_exam)
    ViewGroup rootLayout;

    @Bind(R.id.empty_deck)
    LinearLayout emptyDeck;

    private DeckPagerAdapter adapterViewPager;
    private int cardCounter;
    private int correctAnswersCounter;
    private Integer noOfQuestions;
    private List<Card> flashcards;
    private DrawerAdapter drawerAdapter;
    private String deckTitle;
    private String deckId;
    private Bundle savedState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        initPreDownloadView();
        Intent i = getIntent();
        deckTitle = i.getStringExtra("deckName");
        deckId = i.getStringExtra("deckId");
        savedState = savedInstanceState;

        //if (i.hasExtra("flashcardsOnlyWrong")) {
            //flashcards = i.getParcelableExtra("flashcardsOnlyWrong");
        //    Toast.makeText(this, "przekazano talie", Toast.LENGTH_LONG).show();
        //}
        Toast.makeText(this, "wczytano fiszki od nowa", Toast.LENGTH_SHORT).show();

        downloadFlashcards();
    }

    private void initPreDownloadView() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setUpEnterTransition();
        setUpNavigationDrawer();
        emptyDeck.setVisibility(View.GONE);
    }

    private void initView() {
        if (flashcards.size() != 0) {
            setUpTextToViews();
            setUpPagerAdapter();
        } else {
            emptyDeck.setVisibility(View.VISIBLE);
        }
    }

    private void setUpTextToViews() {
        deckName.setText(deckTitle);
        questionNo.setText(getString(R.string.question_no, cardCounter));
        correctAnswers.setText(getString(
                R.string.correct_answers, correctAnswersCounter, noOfQuestions));
    }

    private void setUpPagerAdapter() {
        adapterViewPager =
                new DeckPagerAdapter(getSupportFragmentManager(), flashcards, PRE_LOAD_IMAGE_COUNT, this);
        viewPager.setAdapter(adapterViewPager);
    }

    private void setUpEnterTransition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Explode transition = new Explode();
            transition.setDuration(TRANSITION_DURATION);
            getWindow().setEnterTransition(transition);
        }
    }

    private void setUpNavigationDrawer() {
        drawerAdapter = new DrawerAdapter(this, navigationView, drawerLayout, toolbar);
        drawerAdapter.attachDrawer();
    }

    private void setUpVariables() {
        noOfQuestions = flashcards.size();
        cardCounter = 1;
    }

    private void updateCard(boolean addCorrectAnswer) {
        updateCounters(addCorrectAnswer);
        setCard();
    }

    private void setCard() {
        if (cardCounter - 1 == noOfQuestions) {
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
                R.string.correct_answers, (cardCounter - 1), noOfQuestions));
    }

    private void displayResult() {
        ResultDialogFragment resultDialog = ResultDialogFragment.newInstance(correctAnswersCounter, flashcards.size());
        resultDialog.show(getSupportFragmentManager(), TAG_RESULT);
    }

    private void restartExam() {
        setInitialValues();
        adapterViewPager.onResultDisplay();
        setFirstCard();
    }

    private void updateCounters(boolean addCorrectAnswer) {
        updateCorrectAnswersCounter(addCorrectAnswer);
        cardCounter++;
    }

    private void updateCorrectAnswersCounter(boolean addCorrectAnswer) {
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

    private void downloadFlashcards() {
        dataHelper.downloadFlashcard(deckId, this);
    }

    private void setUpEnterAnimation() {
        ViewTreeObserver viewTreeObserver = rootLayout.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onGlobalLayout() {
                    int centerX = (rootLayout.getLeft() + rootLayout.getRight()) / 2;
                    int centerY = rootLayout.getTop();
                    int endRadius = Math.max(rootLayout.getWidth(), rootLayout.getHeight());

                    Animator animator = ViewAnimationUtils.createCircularReveal(rootLayout, centerX, centerY, 0, endRadius);
                    rootLayout.setBackgroundColor(ContextCompat.getColor(rootLayout.getContext(), R.color.white));
                    animator.setDuration(ANIMATION_DURATION);
                    animator.start();

                    rootLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            });
        }
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

    @Override
    public void onSuccess(String response) {
        flashcards = dataHelper.getFlashcards();
        setUpVariables();
        initView();
        if (savedState == null) {
            if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                setUpEnterAnimation();
            }
        }
    }

    @Override
    public void onFailure(RetrofitError error) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(connectionStatusReceiver, ConnectionStatusReceiver.filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(connectionStatusReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        drawerAdapter.detachDrawer();
    }

    /**
     * Applies custom font to every activity that overrides this method
     */
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void handleDialogClose(String newName) {
        deckName.setText(newName);
        Toast.makeText(this, "przeladowanie tytulu", Toast.LENGTH_SHORT).show();
    }
}
