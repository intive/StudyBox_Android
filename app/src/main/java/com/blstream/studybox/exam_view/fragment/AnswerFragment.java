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
import com.blstream.studybox.exam_view.ImageTextDisplayer;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AnswerFragment extends Fragment {

    @Bind(R.id.answer)
    public TextView tvAnswer;

    private ImageView[] answerImageTab;
    private ImageTextDisplayer imgTxtDisplayer;
    private CardsProvider cardsProvider;
    private Activity activity;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_answer, container, false);
        initView(view);
        return view;
    }

    private void initView(View view){
        ButterKnife.bind(this, view);
        FrameLayout frameLayout = (FrameLayout)view.findViewById(R.id.answerContainer);
      //  imgTxtDisplayer.setVariables(getContext(), getActivity().getBaseContext());
        answerImageTab = imgTxtDisplayer.init(frameLayout, tvAnswer, cardsProvider.getFirstAnswers());
    }

    @OnClick({R.id.correct_ans_btn, R.id.incorrect_ans_btn})
    public void onClick(View view) {
        if(view.getId() == R.id.correct_ans_btn)
            ((OnMoveToNextCard)activity).onMoveToNextCard(true);
        else
            ((OnMoveToNextCard)activity).onMoveToNextCard(false);
    }

    public interface OnMoveToNextCard {
        void onMoveToNextCard(boolean addCorrectAnswer);
    }

    public void initOnRestart() {
        imgTxtDisplayer.initOnRestart(answerImageTab, tvAnswer, cardsProvider.getFirstAnswers());
    }

    public void changeData() {
        imgTxtDisplayer.changeData(cardsProvider.getNextAnswer(),
                cardsProvider.getLaterAnswer(), tvAnswer, answerImageTab);
    }

    public void setVariables(ImageTextDisplayer imgTxtDisp, CardsProvider cardsProv) {
        imgTxtDisplayer = imgTxtDisp;
        cardsProvider = cardsProv;
    }
}
