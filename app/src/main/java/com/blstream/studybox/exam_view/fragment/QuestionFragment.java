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
import com.blstream.studybox.exam_view.ImageTextDisplayer;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class QuestionFragment extends Fragment {

    @Bind(R.id.question)
    public TextView tvQuestion;

    @Bind(R.id.prompt)
    public TextView tvPrompt;

    private static final String PROMPT = "Podpowiedz";
    private ImageView[] questionImageTab;
    private ImageTextDisplayer imgTxtDisplayer;
    private CardsProvider cardsProvider;
    private String prompt;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);
        initView(view);
        return view;
    }

    public void initView(View view) {
        ButterKnife.bind(this, view);
        FrameLayout frameLayout = (FrameLayout)view.findViewById(R.id.questionContainer);

        prompt = cardsProvider.getFirstPrompt();
    //    imgTxtDisplayer.setVariables(getContext(), getActivity());
        questionImageTab = imgTxtDisplayer.init(frameLayout, tvQuestion, cardsProvider.getFirstQuestions());
    }

    public void initOnRestart(){
        this.prompt = cardsProvider.getFirstPrompt();
        imgTxtDisplayer.initOnRestart(questionImageTab, tvQuestion, cardsProvider.getFirstQuestions());
        setPromptView();
    }

    public void changeData() {
        this.prompt = cardsProvider.getNextPrompt();
        setPromptView();
        imgTxtDisplayer.changeData(cardsProvider.getNextQuestion(),
                cardsProvider.getLaterQuestion(), tvQuestion, questionImageTab);
    }

    public void setPromptView() {
        if(prompt.equals("")) {
            tvPrompt.setTextColor(Color.GRAY);
            tvPrompt.setClickable(false);
        } else {
            tvPrompt.setClickable(true);
            tvPrompt.setTextColor(Color.WHITE);
        }
        tvPrompt.setText(PROMPT);
    }

    @OnClick (R.id.prompt)
    public void onClick(View view) {
        tvPrompt.setText(prompt);
        tvPrompt.setClickable(false);
    }

    public void setVariables(ImageTextDisplayer imgTxtDisp, CardsProvider cardsProv) {
        imgTxtDisplayer = imgTxtDisp;
        cardsProvider = cardsProv;
    }
}
