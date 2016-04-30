package com.blstream.studybox.exam_view.question;

import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

public class QuestionViewState<V extends QuestionView> implements ViewState<V> {

    protected final static int STATE_SHOW_QUESTION_TEXT = 0;
    protected final static int STATE_SHOW_QUESTION_IMAGE = 1;
    protected final static int STATE_SHOW_PROMPT_TEXT = 2;
    protected final static int STATE_SHOW_PROMPT_IMAGE = 3;
    protected final static int STATE_ENABLE_PROMPT = 4;
    protected final static int STATE_DISABLE_PROMPT = 5;

    protected String question;
    protected String prompt;

    protected int questionState = STATE_SHOW_QUESTION_TEXT;
    protected int promptState = STATE_ENABLE_PROMPT;


    public void setStateShowQuestionText(String question) {
        this.question = question;
        questionState = STATE_SHOW_QUESTION_TEXT;
    }

    public void setStateShowQuestionImage(String question) {
        this.question = question;
        questionState = STATE_SHOW_QUESTION_IMAGE;
    }

    public void setStateShowPromptText(String prompt) {
        this.prompt = prompt;
        promptState = STATE_SHOW_PROMPT_TEXT;
    }

    public void setStateShowPromptImage(String prompt) {
        this.prompt = prompt;
        promptState = STATE_SHOW_PROMPT_IMAGE;
    }

    public void setStateEnablePrompt() {
        promptState = STATE_ENABLE_PROMPT;
    }

    public void setStateDisablePrompt() {
        promptState = STATE_DISABLE_PROMPT;
    }

    @Override
    public void apply(V view, boolean retained) {
        switch (questionState) {
            case STATE_SHOW_QUESTION_TEXT:
                view.showTextQuestion(question);
                break;
            case STATE_SHOW_QUESTION_IMAGE:
                view.showImageQuestion(question);
                break;
        }

        switch (promptState) {
            case STATE_SHOW_PROMPT_TEXT:
                view.enablePrompt();
                view.showTextPrompt(prompt);
                break;
            case STATE_SHOW_PROMPT_IMAGE:
                view.enablePrompt();
                view.showImagePrompt(prompt);
                break;
            case STATE_ENABLE_PROMPT:
                view.enablePrompt();
                break;
            case STATE_DISABLE_PROMPT:
                view.disablePrompt();
                break;
        }
    }
}
