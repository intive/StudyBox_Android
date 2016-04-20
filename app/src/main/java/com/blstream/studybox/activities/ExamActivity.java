package com.blstream.studybox.activities;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Context;
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

public class ExamActivity extends AppCompatActivity implements AnswerFragment.OnMoveToNextCard, ResultDialogFragment.OnResultShow, RequestListener<String>, ResultDialogFragment.CloseResultDialogFragmentListener {

    private static final String TAG_RESULT = "result";
    private static final String TAG_DECK_NAME = "deckName";
    private static final String TAG_DECK_ID = "deckId";
    private static final String TAG_CORRECT_ANSWERS_COUNTER = "correctAnswersCounter";
    private static final String TAG_CARDS_COUNTER = "cardsCounter";
    private static final String TAG_NO_OF_QUESTIONS = "noOfQuestions";
    private static final String TAG_FLASHCARDS = "flashcards";
    private static final String TAG_FLASHCARDS_ALL = "flashcardsAll";
    private static final String TAG_FLASHCARDS_ONLY_WRONG = "flashcardsOnlyWrong";
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
    private int cardsCounter;
    private int correctAnswersCounter;
    private Integer noOfQuestions;
    private List<Card> flashcards;
    private List<Card> flashcardsAll;
    private List<Card> flashcardsOnlyWrong;
    private DrawerAdapter drawerAdapter;
    private String deckTitle;
    private String deckId;
    private Bundle savedState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        initPreDownloadView();
        savedState = savedInstanceState;
        checkSavedState();


     //   downloadFlashcards();
    }

    private void checkSavedState(){
        if (savedState == null) {
            Bundle extras = getIntent().getExtras();
            deckTitle = extras.getString(TAG_DECK_NAME);
            deckId = extras.getString(TAG_DECK_ID);
            flashcardsOnlyWrong = new ArrayList<>();
            flashcardsAll = new ArrayList<>();

            downloadFlashcards();
        } else {
            deckTitle = (String) savedState.getSerializable(TAG_DECK_NAME);
            deckId = (String) savedState.getSerializable(TAG_DECK_ID);
            correctAnswersCounter = savedState.getInt(TAG_CORRECT_ANSWERS_COUNTER);
            cardsCounter = savedState.getInt(TAG_CARDS_COUNTER);
            noOfQuestions = savedState.getInt(TAG_NO_OF_QUESTIONS);
            flashcards = savedState.getParcelableArrayList(TAG_FLASHCARDS);
            flashcardsAll = savedState.getParcelableArrayList(TAG_FLASHCARDS_ALL);
            flashcardsOnlyWrong = savedState.getParcelableArrayList(TAG_FLASHCARDS_ONLY_WRONG);

            initView();
        }
    }

    private void initPreDownloadView(){
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setUpEnterTransition();
        setUpNavigationDrawer();
        emptyDeck.setVisibility(View.GONE);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(TAG_DECK_NAME, deckTitle);
        savedInstanceState.putString(TAG_DECK_ID, deckId);
        savedInstanceState.putInt(TAG_CORRECT_ANSWERS_COUNTER, correctAnswersCounter);
        savedInstanceState.putInt(TAG_CARDS_COUNTER, cardsCounter);
        savedInstanceState.putInt(TAG_NO_OF_QUESTIONS, noOfQuestions);
        savedInstanceState.putParcelableArrayList(TAG_FLASHCARDS, (ArrayList<Card>) flashcards);
        savedInstanceState.putParcelableArrayList(TAG_FLASHCARDS_ALL, (ArrayList<Card>) flashcardsAll);
        savedInstanceState.putParcelableArrayList(TAG_FLASHCARDS_ONLY_WRONG, (ArrayList<Card>) flashcardsOnlyWrong);
        super.onSaveInstanceState(savedInstanceState);
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
        questionNo.setText(getString(R.string.question_no, cardsCounter));
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
        cardsCounter = 1;
    }

    private void updateCard(boolean addCorrectAnswer) {
        updateCounters(addCorrectAnswer);
        setCard();
    }

    private void setCard() {
        if (cardsCounter - 1 == noOfQuestions) {
            displayResult();
        } else {
            displayNextCard();
        }
    }

    private void displayNextCard() {
        viewPager.setCurrentItem(0, false);
        adapterViewPager.changeData();
        questionNo.setText(getString(R.string.question_no, cardsCounter));
        correctAnswers.setText(getString(
                R.string.correct_answers, correctAnswersCounter, noOfQuestions));
    }

    private void displayResult() {
        ResultDialogFragment resultDialog = ResultDialogFragment.newInstance(
                correctAnswersCounter, flashcards.size());
        resultDialog.show(getSupportFragmentManager(), TAG_RESULT);
    }

    private void restartExam() {
        setInitialValues();
        adapterViewPager.onResultDisplay();
        setFirstCard();
    }

    private void updateCounters(boolean addCorrectAnswer) {
        updateCorrectAnswersCounter(addCorrectAnswer);
        cardsCounter++;
    }

    private void updateCorrectAnswersCounter(boolean addCorrectAnswer) {
        if (addCorrectAnswer) {
            correctAnswersCounter++;
        } else {
            flashcardsOnlyWrong.add(flashcards.get(cardsCounter - 1));
        }
    }

    private void setInitialValues() {
        cardsCounter = 1;
        correctAnswersCounter = 0;
        noOfQuestions = flashcards.size();
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
        flashcardsAll = new ArrayList<>(flashcards);
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
    public void onBackPressed() {
        this.finish();
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
    public void handleImproveOnlyWrong() {
        reloadFlashcardsFromList(flashcardsOnlyWrong);
    }

    @Override
    public void handleImproveAll() {
        reloadFlashcardsFromList(flashcardsAll);
    }

    private void reloadFlashcardsFromList(List<Card> reloadedFlashcards){
        flashcards = new ArrayList<>(reloadedFlashcards);
        flashcardsOnlyWrong.clear();
        adapterViewPager.changeFlashcards(flashcards);
        restartExam();
    }
}
