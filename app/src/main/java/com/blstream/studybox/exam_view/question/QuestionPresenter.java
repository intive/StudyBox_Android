package com.blstream.studybox.exam_view.question;

import android.util.Patterns;

import com.blstream.studybox.events.ShowAnswerEvent;
import com.blstream.studybox.model.database.Card;
import com.blstream.studybox.model.database.Tip;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class QuestionPresenter extends MvpBasePresenter<QuestionView> {

    protected Card card;
    protected List<Tip> prompts;

    public void loadQuestion(String cardId) {
        showQuestion();
        loadPrompts();
    }

    private void loadPrompts() {
        String prompt = "test Prompt";

        if (prompt.isEmpty()) {
            if (isViewAttached()) {
                getView().disablePrompt();
            }
        } else {
            if (isViewAttached()) {
                getView().enablePrompt();
            }
        }
    }

    protected void showQuestion() {
        String question = "Pytanie 1"; //card.getQuestion();

        if (Patterns.WEB_URL.matcher(question).matches()) {
            if (isViewAttached()) {
                getView().showImageQuestion(question);
            }
        } else {
            if (isViewAttached()) {
                getView().showTextQuestion(question);
            }
        }
    }

    public void showPrompt() {
        String prompt = "http://i.imgur.com/DvpvklR.png";

        if (Patterns.WEB_URL.matcher(prompt).matches()) {
            if (isViewAttached()) {
                getView().showImagePrompt(prompt);
            }
        } else {
            if (isViewAttached()) {
                getView().showTextPrompt(prompt);
            }
        }
    }

    public void showAnswer() {
        EventBus.getDefault().post(new ShowAnswerEvent());
    }
}
