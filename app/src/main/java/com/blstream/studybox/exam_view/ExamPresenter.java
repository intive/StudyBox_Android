package com.blstream.studybox.exam_view;

import com.blstream.studybox.api.RequestListener;
import com.blstream.studybox.database.DataHelper;
import com.blstream.studybox.debugger.DebugHelper;
import com.blstream.studybox.model.database.Card;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.util.List;

import retrofit.RetrofitError;

public class ExamPresenter extends MvpBasePresenter<ExamView> {

    protected final static int FIRST_CARD_NUMBER = 1;
    protected List<Card> flashcards;
    protected int currentCard;
    protected int totalCards;
    protected CardsProvider cardsProvider;

    public void getFlashcards(String deckId) {

        final DataHelper dataHelper = new DataHelper();
        dataHelper.downloadFlashcard(deckId, new RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
                flashcards = dataHelper.getFlashcards();
                DebugHelper.logString("downloaded flashcards");
            }

            @Override
            public void onFailure(RetrofitError error) {

            }
        });

        cardsProvider = new CardsProvider(flashcards);
        setCardsNumber(FIRST_CARD_NUMBER);
    }

    protected void setCardsNumber(int firstCard) {
        totalCards = flashcards.size();
        if (totalCards <= 0) {
            if (isViewAttached()) {
                getView().startEmptyDeckActivity();
            }
        } else {
            currentCard = firstCard;
            if (isViewAttached()) {
                getView().showCardCounter(currentCard, totalCards);
            }
        }
    }
}
