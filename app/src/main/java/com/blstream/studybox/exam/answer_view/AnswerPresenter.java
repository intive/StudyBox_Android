package com.blstream.studybox.exam.answer_view;

import android.util.Patterns;

import com.blstream.studybox.events.CorrectAnswerEvent;
import com.blstream.studybox.events.SkipAnswerEvent;
import com.blstream.studybox.events.WrongAnswerEvent;
import com.blstream.studybox.model.database.Card;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import org.greenrobot.eventbus.EventBus;

public class AnswerPresenter extends MvpBasePresenter<AnswerView> {

    public void loadAnswer(String cardId) {
        Card card = Card.getCardById(cardId);
        showAnswer(card);
    }

    private void showAnswer(Card card) {
        String answer = card.getAnswer();

        if (Patterns.WEB_URL.matcher(answer).matches()) {
            if (isViewAttached()) {
                //noinspection ConstantConditions
                getView().showImageAnswer(answer);
            }
        } else {
            if (isViewAttached()) {
                //noinspection ConstantConditions
                getView().showTextAnswer(answer);
            }
        }
    }

    public void sendCorrectAnswer() {
        EventBus.getDefault().post(new CorrectAnswerEvent());
    }

    public void sendWrongAnswer() {
        EventBus.getDefault().post(new WrongAnswerEvent());
    }

    public void sendSkipAnswer() {
        EventBus.getDefault().post(new SkipAnswerEvent());
    }

}
