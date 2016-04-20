package com.blstream.studybox.exam_view.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.blstream.studybox.R;
import com.blstream.studybox.exam_view.CardsProvider;
import com.blstream.studybox.exam_view.ImageTextDisplay;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AnswerFragment extends Fragment {

    private static final String TAG_CARDS_PROVIDER = "cardsProvider";
    private static final String TAG_IMAGE_TEXT_DISPLAY = "imageTextDisplay";

    @Bind(R.id.answer)
    TextView tvAnswer;

    private ImageView[] answerImageTab;
    private ImageTextDisplay imageTextDisplay;
    private CardsProvider cardsProvider;
    private Activity activity;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putParcelable(TAG_CARDS_PROVIDER,  cardsProvider);
        savedInstanceState.putParcelable(TAG_IMAGE_TEXT_DISPLAY, imageTextDisplay);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_answer, container, false);
        initView(view, savedInstanceState);
        return view;
    }

    private void initView(View view, Bundle savedInstanceState){
        ButterKnife.bind(this, view);
        FrameLayout frameLayout = (FrameLayout)view.findViewById(R.id.answerContainer);
        checkSavedState(savedInstanceState, frameLayout);
    }

    private void checkSavedState(Bundle savedInstanceState, FrameLayout frameLayout){
        if (savedInstanceState == null) {
            answerImageTab = imageTextDisplay.init(frameLayout, tvAnswer,
                    cardsProvider.getFirstAnswers(), activity);
        } else {
            cardsProvider = savedInstanceState.getParcelable(TAG_CARDS_PROVIDER);
            imageTextDisplay = savedInstanceState.getParcelable(TAG_IMAGE_TEXT_DISPLAY);
            assert imageTextDisplay != null;
            answerImageTab = imageTextDisplay.init(frameLayout, tvAnswer,
                    cardsProvider.getCurrentFewAnswers(), activity);
        }
    }

    @OnClick({R.id.correct_ans_btn, R.id.incorrect_ans_btn})
    public void onClick(View view) {
        if(view.getId() == R.id.correct_ans_btn) {
            ((OnMoveToNextCard) activity).onMoveToNextCard(true);
        } else {
            ((OnMoveToNextCard) activity).onMoveToNextCard(false);
        }
    }

    public interface OnMoveToNextCard {
        void onMoveToNextCard(boolean addCorrectAnswer);
    }

    public void initOnRestart() {
        imageTextDisplay.initOnRestart(answerImageTab, tvAnswer,
                cardsProvider.getFirstAnswers(), activity);
    }

    public void changeData() {
        imageTextDisplay.changeData(cardsProvider.getNextAnswer(),
                cardsProvider.getLaterAnswer(), tvAnswer, answerImageTab, activity);
    }

    public void setVariables(ImageTextDisplay imageTextDisplay, CardsProvider cardsProvider) {
        this.imageTextDisplay = imageTextDisplay;
        this.cardsProvider = cardsProvider;
    }
}
