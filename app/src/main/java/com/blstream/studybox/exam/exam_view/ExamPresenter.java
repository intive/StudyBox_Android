package com.blstream.studybox.exam.exam_view;

import android.content.Context;

import com.blstream.studybox.database.DataHelper;
import com.blstream.studybox.database.DataProvider;
import com.blstream.studybox.events.CorrectAnswerEvent;
import com.blstream.studybox.events.ImproveAllEvent;
import com.blstream.studybox.events.ImproveWrongEvent;
import com.blstream.studybox.events.ShowAnswerEvent;
import com.blstream.studybox.events.WrongAnswerEvent;
import com.blstream.studybox.model.database.Card;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class ExamPresenter extends MvpBasePresenter<ExamView> {

    protected final static int FIRST_CARD_NUMBER = 1;
    protected List<Card> flashcards;
    protected List<Card> wrongAnsweredCards;
    protected int currentCardNumber;
    protected String currentCardId;
    protected int totalCards;
    protected int correctAnswers;
    protected Context context;

    public ExamPresenter(Context context) {
        this.context = context;
        wrongAnsweredCards = new ArrayList<>();
        flashcards = new ArrayList<>();
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
                flashcards = cards;
                initExam();
            }
        });
    }

    protected void initExam() {
        totalCards = flashcards.size();
        correctAnswers = 0;
        currentCardNumber = FIRST_CARD_NUMBER;
        if (totalCards <= 0) {
            if (isViewAttached()) {
                // TODO move this to exam view when number of flashcards will be provided by backend
                getView().startEmptyDeckActivity();
            }
        } else {
            currentCardId = flashcards.get(currentCardNumber - 1).getCardId();
            if (isViewAttached()) {
                getView().setCardCounter(currentCardNumber, totalCards);
                getView().showQuestion(currentCardId);
            }
        }
    }

    protected void updateCardCounter() {
        totalCards = flashcards.size();
        if (isViewAttached()) {
            getView().setCardCounter(currentCardNumber, totalCards);
        }
    }

    @Subscribe
    public void onShowAnswerEvent(ShowAnswerEvent event) {
        if (isViewAttached()) {
            getView().showAnswer(currentCardId);
        }
    }

    @Subscribe
    public void onCorrectAnswerEvent(CorrectAnswerEvent event) {
        correctAnswers++;
        moveToNextCard();
    }

    @Subscribe
    public void onWrongAnswerEvent(WrongAnswerEvent event) {
        wrongAnsweredCards.add(flashcards.get(currentCardNumber - 1));
        moveToNextCard();
    }

    @Subscribe
    public void onImproveAllEvent(ImproveAllEvent event) {
        initExam();
    }

    @Subscribe
    public void onImproveWrongEvent(ImproveWrongEvent event) {
        flashcards = new ArrayList<>(wrongAnsweredCards);
        wrongAnsweredCards.clear();
        initExam();
    }

    protected void moveToNextCard() {
        currentCardNumber++;
        if (currentCardNumber > totalCards) {
            if (isViewAttached()) {
                getView().showResult(correctAnswers, totalCards);
            }
        } else {
            currentCardId = flashcards.get(currentCardNumber - 1).getCardId();
            if (isViewAttached()) {
                getView().showQuestion(currentCardId);
                updateCardCounter();
            }
        }
    }
}
