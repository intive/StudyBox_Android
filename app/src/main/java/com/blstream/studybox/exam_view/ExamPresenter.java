package com.blstream.studybox.exam_view;

import com.blstream.studybox.events.CorrectAnswerEvent;
import com.blstream.studybox.events.WrongAnswerEvent;
import com.blstream.studybox.model.database.Card;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class ExamPresenter extends MvpBasePresenter<ExamView> {

    protected final static int FIRST_CARD_NUMBER = 1;
    protected List<Card> flashcards;
    protected int currentCard;
    protected int totalCards;
    protected CardsProvider cardsProvider;

    public void getFlashcards(String deckId) {
        cardsProvider = new CardsProvider(deckId);
        setCardsNumber(FIRST_CARD_NUMBER);
    }

    protected void setCardsNumber(int firstCard) {
        totalCards = cardsProvider.getTotalCardsNumber();
        if (totalCards <= 0) {
            if (isViewAttached()) {
                // TODO move this to exam view when number of flashcards will be provided by backend
                getView().startEmptyDeckActivity();
            }
        } else {
            currentCard = firstCard;
            if (isViewAttached()) {
                getView().setCardCounter(currentCard, totalCards);
            }
        }
    }

    @Subscribe
    public void onCorrectAnswerEvent(CorrectAnswerEvent event) {
        updateCardCounter();
    }

    @Subscribe
    public void onWrongAnswerEvent(WrongAnswerEvent event) {
        updateCardCounter();
    }

    protected void updateCardCounter() {
        currentCard = cardsProvider.getCurrentCardNumber();
        totalCards = cardsProvider.getTotalCardsNumber();
        if (isViewAttached()) {
            getView().setCardCounter(currentCard, totalCards);
        }
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
}
