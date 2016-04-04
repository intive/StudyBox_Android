package com.blstream.studybox.exam_view.fragment;

import android.graphics.Color;
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


public class QuestionFragment extends Fragment {

    @Bind(R.id.question)
    TextView questionView;

    @Bind(R.id.prompt)
    TextView promptView;

    private ImageView[] questionImageTab;
    private ImageTextDisplay imgTxtDisplay;
    private CardsProvider cardsProvider;
    private String prompt;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        ButterKnife.bind(this, view);
        FrameLayout frameLayout = (FrameLayout)view.findViewById(R.id.questionContainer);

        prompt = cardsProvider.getFirstPrompt();
        questionImageTab = imgTxtDisplay.init(frameLayout, questionView, cardsProvider.getFirstQuestions());
    }

    public void initOnRestart(){
        this.prompt = cardsProvider.getFirstPrompt();
        imgTxtDisplay.initOnRestart(questionImageTab, questionView, cardsProvider.getFirstQuestions());
        setPromptView();
    }

    public void changeData() {
        this.prompt = cardsProvider.getNextPrompt();
        setPromptView();
        imgTxtDisplay.changeData(cardsProvider.getNextQuestion(),
                cardsProvider.getLaterQuestion(), questionView, questionImageTab);
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
