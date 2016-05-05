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

    private Card card;
    private List<Tip> prompts;
    private Context context;
    private boolean isInPromptMode;
    private int promptPosition;

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
                //noinspection ConstantConditions
                getView().disablePrompt();
            }
        } else {
            if (isViewAttached()) {
                //noinspection ConstantConditions
                getView().enablePrompt();
            }
            showPrompt(promptPosition);
        }
    }

    protected void showQuestion() {
        String question = card.getQuestion();

        if (Patterns.WEB_URL.matcher(question).matches()) {
            if (isViewAttached()) {
                //noinspection ConstantConditions
                getView().showImageQuestion(question);
            }
        } else {
            if (isViewAttached()) {
                //noinspection ConstantConditions
                getView().showTextQuestion(question);
            }
        }
    }

    public void showPrompt(int promptPos) {
        promptPosition = promptPos;
        String prompt = prompts.get(promptPosition).getEssence();

        if (Patterns.WEB_URL.matcher(prompt).matches()) {
            if (isViewAttached()) {
                //noinspection ConstantConditions
                getView().showImagePrompt(prompt);
            }
        } else {
            if (isViewAttached()) {
                //noinspection ConstantConditions
                getView().showTextPrompt(prompt);
            }
        }
        setPromptArrowsVisibility();
    }

    private void setPromptArrowsVisibility() {
        if (promptPosition == 0) {
            if (isViewAttached()) {
                //noinspection ConstantConditions
                getView().hideLeftPromptArrow();
            }
        } else {
            if (isViewAttached()) {
                //noinspection ConstantConditions
                getView().showLeftPromptArrow();
            }
        }

        if (promptPosition == prompts.size() - 1) {
            if (isViewAttached()) {
                //noinspection ConstantConditions
                getView().hideRightPromptArrow();
            }
        } else {
            if (isViewAttached()) {
                //noinspection ConstantConditions
                getView().showRightPromptArrow();
            }
        }
    }

    public void showAnswer() {
        EventBus.getDefault().post(new ShowAnswerEvent());
    }

    public void switchView() {
        if (!isInPromptMode) {
            if (isViewAttached()) {
                //noinspection ConstantConditions
                getView().switchToPrompts();
            }
        } else {
            if (isViewAttached()) {
                //noinspection ConstantConditions
                getView().switchToQuestion();
            }
        }
    }

    public void setView(boolean inPromptMode){
        isInPromptMode = inPromptMode;
        if (isInPromptMode) {
            if (isViewAttached()) {
                //noinspection ConstantConditions
                getView().switchToPrompts();
            }
        }
    }

    public  void showNextPrompt(int promptPos){
        promptPosition = promptPos;
    }

    public  void showPrevPrompt(int promptPos){
        promptPosition = promptPos;
    }

    public void inPrompt(boolean isPromptShowed) {
        isInPromptMode = isPromptShowed;
    }

    public void setPromptPosition(int promptPos) {
        promptPosition = promptPos;
    }
}
