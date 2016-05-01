package com.blstream.studybox.exam.question_view;

import android.content.Context;
import android.util.Patterns;

import com.blstream.studybox.database.DataHelper;
import com.blstream.studybox.database.DataProvider;
import com.blstream.studybox.events.ShowAnswerEvent;
import com.blstream.studybox.model.database.Card;
import com.blstream.studybox.model.database.Tip;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class QuestionPresenter extends MvpBasePresenter<QuestionView> {

    protected Card card;
    protected List<Tip> prompts;
    protected Context context;

    public QuestionPresenter(Context context) {
        this.context = context;
        prompts = new ArrayList<>();
    }

    public void loadQuestion(String cardId) {
        card = Card.getCardById(cardId);
        showQuestion();
        loadPrompts();
    }

    private void loadPrompts() {
        final DataProvider dataProvider = new DataHelper(context);
        dataProvider.fetchTips(card.getDeckId(), card.getCardId(), new DataProvider.OnTipsReceivedListener<List<Tip>>() {
            @Override
            public void OnTipsReceived(List<Tip> tips) {
                prompts = tips;
                initPrompts();
            }
        });
    }

    protected void initPrompts() {
        if (prompts.isEmpty()) {
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
        String question = card.getQuestion();

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
        String prompt = prompts.get(0).getEssence();

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
