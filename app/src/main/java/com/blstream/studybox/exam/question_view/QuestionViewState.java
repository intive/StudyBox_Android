package com.blstream.studybox.exam.question_view;

import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

public class QuestionViewState<V extends QuestionView> implements ViewState<V> {

    protected final static int STATE_SHOW_QUESTION_TEXT = 0;
    protected final static int STATE_SHOW_QUESTION_IMAGE = 1;
    protected final static int STATE_SHOW_PROMPT_TEXT = 2;
    protected final static int STATE_SHOW_PROMPT_IMAGE = 3;
    protected final static int STATE_ENABLE_PROMPT = 4;
    protected final static int STATE_DISABLE_PROMPT = 5;
    protected final static int STATE_SHOW_QUESTION = 6;
    protected final static int STATE_SHOW_PROMPT = 7;
    protected final static int STATE_SHOW_LEFT_PROMPT_ARROW = 8;
    protected final static int STATE_HIDE_LEFT_PROMPT_ARROW = 9;
    protected final static int STATE_SHOW_RIGHT_PROMPT_ARROW = 10;
    protected final static int STATE_HIDE_RIGHT_PROMPT_ARROW = 11;

    protected String question;
    protected String prompt;
    protected int promptPosition;

    protected int viewSwitcherState = STATE_SHOW_QUESTION;
    protected int questionState = STATE_SHOW_QUESTION_TEXT;
    protected int promptState = STATE_DISABLE_PROMPT;
    protected int leftPromptArrowState = STATE_HIDE_LEFT_PROMPT_ARROW ;
    protected int rightPromptArrowState = STATE_HIDE_RIGHT_PROMPT_ARROW;


    public void setStateShowQuestion() {
        viewSwitcherState = STATE_SHOW_QUESTION;
    }

    public void setStateShowPrompt() {
        viewSwitcherState = STATE_SHOW_PROMPT;
    }

    public void setStateShowQuestionText(String question) {
        this.question = question;
        questionState = STATE_SHOW_QUESTION_TEXT;
    }

    public void setStateShowQuestionImage(String question) {
        this.question = question;
        questionState = STATE_SHOW_QUESTION_IMAGE;
    }

    public void setStateShowPromptText(String prompt, int promptPosition) {
        this.prompt = prompt;
        this.promptPosition = promptPosition;
        promptState = STATE_SHOW_PROMPT_TEXT;
    }

    public void setStateShowPromptImage(String prompt, int promptPosition) {
        this.prompt = prompt;
        this.promptPosition = promptPosition;
        promptState = STATE_SHOW_PROMPT_IMAGE;
    }

    public void setStateShowLeftPromptArrow() {
        leftPromptArrowState = STATE_SHOW_LEFT_PROMPT_ARROW;
    }

    public void setStateHideLeftPromptArrow() {
        leftPromptArrowState = STATE_HIDE_LEFT_PROMPT_ARROW;
    }

    public void setStateShowRightPromptArrow() {
        rightPromptArrowState = STATE_SHOW_RIGHT_PROMPT_ARROW;
    }

    public void setStateHideRightPromptArrow() {
        rightPromptArrowState = STATE_HIDE_RIGHT_PROMPT_ARROW;
    }

    public void setStateEnablePrompt() {
        promptState = STATE_ENABLE_PROMPT;
    }

    public void setStateDisablePrompt() {
        promptState = STATE_DISABLE_PROMPT;
    }

    @Override
    public void apply(V view, boolean retained) {
        switch (viewSwitcherState) {
            case STATE_SHOW_QUESTION:
                view.inPromptMode(false);
                break;
            case STATE_SHOW_PROMPT:
                view.inPromptMode(true);
                break;
        }

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
                view.setPromptPosition(promptPosition);
                checkLeftPromptArrowState(view);
                checkRightPromptArrowState(view);
                break;
            case STATE_SHOW_PROMPT_IMAGE:
                view.enablePrompt();
                view.showImagePrompt(prompt);
                view.setPromptPosition(promptPosition);
                checkLeftPromptArrowState(view);
                checkRightPromptArrowState(view);
                break;
            case STATE_ENABLE_PROMPT:
                view.enablePrompt();
                break;
            case STATE_DISABLE_PROMPT:
                view.disablePrompt();
                break;
        }
    }

    private void checkLeftPromptArrowState(V view){
        switch (leftPromptArrowState ) {
            case STATE_SHOW_LEFT_PROMPT_ARROW:
                view.showLeftPromptArrow();
                break;
            case STATE_HIDE_LEFT_PROMPT_ARROW:
                view.hideLeftPromptArrow();
                break;
        }
    }

    private void checkRightPromptArrowState(V view){
        switch (rightPromptArrowState ) {
            case STATE_SHOW_RIGHT_PROMPT_ARROW:
                view.showRightPromptArrow();
                break;
            case STATE_HIDE_RIGHT_PROMPT_ARROW:
                view.hideRightPromptArrow();
                break;
        }
    }
}
