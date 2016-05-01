package com.blstream.studybox.activities;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.blstream.studybox.R;
import com.blstream.studybox.base.BaseViewStateActivity;
import com.blstream.studybox.components.DrawerAdapter;
import com.blstream.studybox.exam_view.ExamPresenter;
import com.blstream.studybox.exam_view.ExamView;
import com.blstream.studybox.exam_view.ExamViewState;
import com.blstream.studybox.exam_view.answer.AnswerFragment;
import com.blstream.studybox.exam_view.question.QuestionFragment;
import com.blstream.studybox.exam_view.ResultDialogFragment;
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ExamActivity extends BaseViewStateActivity<ExamView, ExamPresenter>
        implements ExamView {

    private static final String TAG_RESULT = "result";
    private static final String TAG_DECK_NAME = "deckName";
    private static final String TAG_DECK_ID = "deckId";
    private static final String TAG_CARD_ID = "cardId";
    private static final int ANIMATION_DURATION = 1000;
    private static final int TRANSITION_DURATION = 500;

    @Bind(R.id.cardsNumber)
    TextView cardsNumber;

    @Bind(R.id.toolbar_exam)
    Toolbar toolbar;

    @Bind(R.id.nav_view_exam)
    NavigationView navigationView;

    @Bind(R.id.drawer_layout_exam)
    DrawerLayout drawerLayout;

    @Bind(R.id.content_exam)
    ViewGroup rootLayout;

    private DrawerAdapter drawerAdapter;
    private String deckTitle;
    private String deckId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        setRetainInstance(true);
        if (savedInstanceState == null) {
            checkSavedState();
        }
        initView();
    }

    private void checkSavedState(){
        Bundle extras = getIntent().getExtras();
        deckTitle = extras.getString(TAG_DECK_NAME);
        deckId = extras.getString(TAG_DECK_ID);
    }

    private void initView() {
        ButterKnife.bind(this);
        toolbar.setTitle(deckTitle);
        setSupportActionBar(toolbar);
        setUpEnterTransition();
        setUpNavigationDrawer();
    }

    public static void start(Context context, String deckId, String deckName) {
        final Intent intent = new Intent(context, ExamActivity.class);
        intent.putExtra(TAG_DECK_ID, deckId);
        intent.putExtra(TAG_DECK_NAME, deckName);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            context.startActivity(intent,
                    ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context).toBundle());
        } else {
            context.startActivity(intent);
        }
    }

    @NonNull
    @Override
    public ExamPresenter createPresenter() {
        return new ExamPresenter();
    }

    @NonNull
    @Override
    public ViewState<ExamView> createViewState() {
        return new ExamViewState<>();
    }

    @Override
    public void onNewViewStateInstance() {
        presenter.getFlashcards(deckId);
        setupAnimation();
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

    @Override
    public void setCardCounter(int currentCard, int totalCards) {
        ExamViewState vs = (ExamViewState) viewState;
        vs.saveCardCounter(currentCard, totalCards);
        cardsNumber.setText(getString(R.string.correct_answers, currentCard, totalCards));
    }

    @Override
    public void startEmptyDeckActivity() {
        Intent intent = new Intent(this, EmptyDeckActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showQuestion(String cardId) {
        QuestionFragment questionFragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putString(TAG_CARD_ID, cardId);
        questionFragment.setArguments(args);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, questionFragment)
                .commit();
    }

    @Override
    public void showAnswer(String cardId) {
        AnswerFragment answerFragment = new AnswerFragment();
        Bundle args = new Bundle();
        args.putString(TAG_CARD_ID, cardId);
        answerFragment.setArguments(args);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, answerFragment)
                .commit();
    }

    @Override
    public void showResult(int correctAnswers, int totalCards) {
        ResultDialogFragment resultDialog = ResultDialogFragment.newInstance(correctAnswers, totalCards);
        resultDialog.show(getSupportFragmentManager(), TAG_RESULT);
    }

    protected void setupAnimation() {
        if (!isRestoringViewState()) {
            if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                setUpEnterAnimation();
            }
        }
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

    @Override
    public void onBackPressed() {
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        drawerAdapter.detachDrawer();
    }
}
