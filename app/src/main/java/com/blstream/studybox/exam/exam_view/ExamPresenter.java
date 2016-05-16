package com.blstream.studybox.exam.exam_view;

import android.content.Context;

import com.blstream.studybox.data_provider.DataHelper;
import com.blstream.studybox.data_provider.DataProvider;
import com.blstream.studybox.events.CorrectAnswerEvent;
import com.blstream.studybox.events.ImproveAllEvent;
import com.blstream.studybox.events.ImproveWrongEvent;
import com.blstream.studybox.events.ShowAnswerEvent;
import com.blstream.studybox.events.SkipAnswerEvent;
import com.blstream.studybox.events.WrongAnswerEvent;
import com.blstream.studybox.exam.CardPosition;
import com.blstream.studybox.exam.ExamManager;
import com.blstream.studybox.model.database.Card;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class ExamPresenter extends MvpBasePresenter<ExamView> {

    protected int totalCards;
    protected int correctAnswers;
    protected int currentCardNumber;
    protected String currentCardId;
    private ExamManager examManager;
    private final Context context;
    private boolean isInExamMode;

    public ExamPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void attachView(ExamView view) {
        super.attachView(view);
        EventBus.getDefault().register(this);
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        EventBus.getDefault().unregister(this);
    }

    public void getFlashcards(String deckId, String randomAmount) {
        final DataProvider dataProvider = new DataHelper(context);
        dataProvider.fetchFlashcards(deckId, randomAmount, new DataProvider.OnCardsReceivedListener<List<Card>>() {
            @Override
            public void OnCardsReceived(List<Card> cards) {
                if (!cards.isEmpty()) {
                    examManager = new ExamManager(cards);
                    initExam();
                }
            }
        });
    }

    protected void initExam() {
        totalCards = examManager.getTotalCardsNumber();
        currentCardNumber = examManager.getCurrentCardNumber();
        currentCardId = examManager.getCurrentCardId();
        if (isViewAttached()) {
            //noinspection ConstantConditions
            getView().setCardCounter(currentCardNumber, totalCards);
            getView().showQuestion(currentCardId);
        }
    }

    protected void updateCardCounter() {
        totalCards = examManager.getTotalCardsNumber();
        currentCardNumber = examManager.getCurrentCardNumber();
        if (isViewAttached()) {
            //noinspection ConstantConditions
            getView().setCardCounter(currentCardNumber, totalCards);
        }
    }

    @Subscribe
    public void onShowAnswerEvent(ShowAnswerEvent event) {
        if (isViewAttached()) {
            //noinspection ConstantConditions
            getView().showAnswer(currentCardId);
        }
    }

    @Subscribe
    public void onCorrectAnswerEvent(CorrectAnswerEvent event) {
        if(isInExamMode) examManager.setCurrentCardCorrect();
        examManager.setCardPosition(CardPosition.INCREMENT);
        moveToNextCard();
    }

    @Subscribe
    public void onWrongAnswerEvent(WrongAnswerEvent event) {
        if(isInExamMode) {
            examManager.setCurrentCardWrong();
            examManager.setCardPosition(CardPosition.INCREMENT);
        } else {
            examManager.setCardPosition(CardPosition.SHUFFLE);
        }
        moveToNextCard();
    }

    @Subscribe
    public void onSkipAnswerEvent(SkipAnswerEvent event) {
        examManager.setCardPosition(CardPosition.END);
        moveToNextCard();
    }

    @Subscribe
    public void onImproveAllEvent(ImproveAllEvent event) {
        examManager.improveAll();
        initExam();
    }

    @Subscribe
    public void onImproveWrongEvent(ImproveWrongEvent event) {
        examManager.improveWrong();
        initExam();
    }

    protected void moveToNextCard() {
        if (examManager.setNextCard()) {
            currentCardId = examManager.getCurrentCardId();
            if (isViewAttached()) {
                //noinspection ConstantConditions
                getView().showQuestion(currentCardId);
                updateCardCounter();
            }
        } else {
            if (isViewAttached()) {
                correctAnswers = examManager.getCorrectAnswers();
                //noinspection ConstantConditions
                getView().showResult(correctAnswers, totalCards);
            }
        }
    }

    public void inExam(boolean isInExam) {
        isInExamMode = isInExam;
    }
}
