package com.blstream.studybox.exam.exam_view;

import android.content.Context;

import com.blstream.studybox.database.DataHelper;
import com.blstream.studybox.database.DataProvider;
import com.blstream.studybox.events.CorrectAnswerEvent;
import com.blstream.studybox.events.ImproveAllEvent;
import com.blstream.studybox.events.ImproveWrongEvent;
import com.blstream.studybox.events.ShowAnswerEvent;
import com.blstream.studybox.events.WrongAnswerEvent;
import com.blstream.studybox.exam.ExamProvider;
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
    protected Context context;
    protected ExamProvider examProvider;

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

    public void getFlashcards(String deckId) {
        final DataProvider dataProvider = new DataHelper(context);
        dataProvider.fetchFlashcards(deckId, new DataProvider.OnCardsReceivedListener<List<Card>>() {
            @Override
            public void OnCardsReceived(List<Card> cards) {
                if (!cards.isEmpty()) {
                    examProvider = new ExamProvider(cards);
                    initExam();
                } else {
                    if (isViewAttached()) {
                        // TODO move this to decks view when number of flashcards will be provided by backend
                        //noinspection ConstantConditions
                        getView().startEmptyDeckActivity();
                    }
                }
            }
        });
    }

    protected void initExam() {
        totalCards = examProvider.getTotalCardsNumber();
        currentCardNumber = examProvider.getCurrentCardNumber();
        currentCardId = examProvider.getCurrentCardId();
        if (isViewAttached()) {
            //noinspection ConstantConditions
            getView().setCardCounter(currentCardNumber, totalCards);
            getView().showQuestion(currentCardId);
        }
    }

    protected void updateCardCounter() {
        totalCards = examProvider.getTotalCardsNumber();
        currentCardNumber = examProvider.getCurrentCardNumber();
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
        examProvider.setCurrentCardCorrect();
        moveToNextCard();
    }

    @Subscribe
    public void onWrongAnswerEvent(WrongAnswerEvent event) {
        examProvider.setCurrentCardWrong();
        moveToNextCard();
    }

    @Subscribe
    public void onImproveAllEvent(ImproveAllEvent event) {
        examProvider.improveAll();
        initExam();
    }

    @Subscribe
    public void onImproveWrongEvent(ImproveWrongEvent event) {
        examProvider.improveWrong();
        initExam();
    }

    protected void moveToNextCard() {
        if (examProvider.setNextCard()) {
            currentCardId = examProvider.getCurrentCardId();
            if (isViewAttached()) {
                //noinspection ConstantConditions
                getView().showQuestion(currentCardId);
                updateCardCounter();
            }
        } else {
            if (isViewAttached()) {
                correctAnswers = examProvider.getCorrectAnswers();
                //noinspection ConstantConditions
                getView().showResult(correctAnswers, totalCards);
            }
        }
    }
}
