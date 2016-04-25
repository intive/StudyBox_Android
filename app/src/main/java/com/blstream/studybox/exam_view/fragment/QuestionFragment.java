package com.blstream.studybox.exam_view.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.blstream.studybox.R;
import com.blstream.studybox.exam_view.CardsProvider;
import com.blstream.studybox.exam_view.ImageTextDisplay;
import com.blstream.studybox.exam_view.QuestionPresenter;
import com.blstream.studybox.exam_view.QuestionView;
import com.blstream.studybox.exam_view.BaseQuestionViewState;
import com.hannesdorfmann.mosby.mvp.viewstate.MvpViewStateFragment;
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class QuestionFragment extends MvpViewStateFragment<QuestionView, QuestionPresenter>
        implements QuestionView {

    private static final String TAG_CARDS_PROVIDER = "cardsProvider";
    private static final String TAG_IMAGE_TEXT_DISPLAY = "imageTextDisplay";

    private ImageView[] questionImageTab;
    private ImageTextDisplay imgTxtDisplay;
    private CardsProvider cardsProvider;
    private String prompt;
    private Activity activity;

    @Bind(R.id.question)
    TextView questionView;

    @Bind(R.id.prompt)
    TextView promptView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        setRetainInstance(true);
    }

    @Override @NonNull
    public QuestionPresenter createPresenter() {
        return new QuestionPresenter();
    }

    @Override @NonNull
    public ViewState createViewState() {
        return new BaseQuestionViewState();
    }

    @Override
    public void onNewViewStateInstance() {

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        //savedInstanceState.putParcelable(TAG_CARDS_PROVIDER,  cardsProvider);
        savedInstanceState.putParcelable(TAG_IMAGE_TEXT_DISPLAY, imgTxtDisplay);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);
        initView(view, savedInstanceState);
        return view;
    }

    private void initView(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        FrameLayout frameLayout = (FrameLayout)view.findViewById(R.id.questionContainer);
        checkSavedState(savedInstanceState, frameLayout);
    }

    private void checkSavedState(Bundle savedInstanceState, FrameLayout frameLayout){
        if (savedInstanceState == null) {
            prompt = cardsProvider.getFirstPrompt();
            questionImageTab = imgTxtDisplay.init(frameLayout, questionView,
                    cardsProvider.getQuestionsForPreload(), activity);
        } else {
            cardsProvider = savedInstanceState.getParcelable(TAG_CARDS_PROVIDER);
            imgTxtDisplay = savedInstanceState.getParcelable(TAG_IMAGE_TEXT_DISPLAY);
            prompt = cardsProvider.getNextPrompt();
            assert imgTxtDisplay != null;
            questionImageTab = imgTxtDisplay.init(frameLayout, questionView,
                    cardsProvider.getQuestionsForPreload(), activity);
        }
    }

    public void initOnRestart(){
        this.prompt = cardsProvider.getFirstPrompt();
        imgTxtDisplay.initOnRestart(questionImageTab, questionView,
                cardsProvider.getQuestionsForPreload(), activity);
        setPromptView();
    }

    public void changeData() {
        this.prompt = cardsProvider.getNextPrompt();
        setPromptView();
        imgTxtDisplay.changeData(cardsProvider.getNextQuestion(),
                cardsProvider.getLaterQuestion(), questionView, questionImageTab, activity);
    }

    private void setPromptView() {
        if(prompt.equals("")) {
            promptView.setTextColor(Color.GRAY);
            promptView.setClickable(false);
        } else {
            promptView.setClickable(true);
            promptView.setTextColor(Color.WHITE);
        }
        promptView.setText(R.string.prompt);
    }

    @OnClick (R.id.prompt)
    public void onClick(View view) {
        promptView.setText(prompt);
        promptView.setClickable(false);
    }

    public void setVariables(ImageTextDisplay imgTxtDisp, CardsProvider cardsProv) {
        imgTxtDisplay = imgTxtDisp;
        cardsProvider = cardsProv;
    }
}
