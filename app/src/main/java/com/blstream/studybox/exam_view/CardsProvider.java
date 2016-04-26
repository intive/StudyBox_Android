package com.blstream.studybox.exam_view;

import com.blstream.studybox.api.RequestListener;
import com.blstream.studybox.database.DataHelper;
import com.blstream.studybox.debugger.DebugHelper;
import com.blstream.studybox.model.database.Card;
import com.blstream.studybox.model.database.Tip;

import java.util.List;

import retrofit.RetrofitError;

public class CardsProvider {

    private static final String TAG_PROMPT = "PROMPT";
    private List<Card> flashcards;
    private List<Tip> prompts;
    private Card currentCard;
    private int position;

    public CardsProvider(String deckId) {
        downloadCards(deckId);
        position = 0;
    }

    public CardsProvider(List<Card> flashcards) {
        this.flashcards = flashcards;
        position = 0;
    }

    private void downloadCards(String deckId) {
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
    }

    private void downloadPrompts(String deckId, String cardId) {

    }

    public int getTotalCardsNumber() {
        return flashcards.size();
    }

    public int getCurrentCardNumber() {
        return position + 1;
    }

    public Card getCurrentCard() {
        return currentCard;
    }

    public String getQuestion() {
        return currentCard.getQuestion();
    }

    // TODO only for testing, delete
    public String getPrompt() {
        return TAG_PROMPT;
    }

    public String getAnswer() {
        return currentCard.getAnswer();
    }

    public List<Tip> getPrompts() {
        return prompts;
    }

    public void resetPosition() {
        position = 0;
    }

    public int getPosition() {
        return position;
    }

    public void setNextCard() {
        position++;
        if (position < flashcards.size()) {
            currentCard = flashcards.get(position);
        }
    }
}
