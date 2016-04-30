package com.blstream.studybox.exam_view.answer;

import android.util.Patterns;

import com.blstream.studybox.events.CorrectAnswerEvent;
import com.blstream.studybox.events.WrongAnswerEvent;
import com.blstream.studybox.model.database.Card;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import org.greenrobot.eventbus.EventBus;

public class AnswerPresenter extends MvpBasePresenter<AnswerView> {

    protected Card card;

    public void loadAnswer(String cardId) {
        showAnswer();
    }

    protected void showAnswer() {
        String answer = "http://i.imgur.com/DvpvklR.png"; //card.getAnswer();

        if (Patterns.WEB_URL.matcher(answer).matches()) {
            if (isViewAttached()) {
                getView().showImageAnswer(answer);
            }
        } else {
            if (isViewAttached()) {
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

}
