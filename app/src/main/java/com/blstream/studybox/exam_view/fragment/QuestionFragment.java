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
    private String[] questions;
    private String prompt;
    private ImageView[] questionImageTab;
    private ImageTextDisplayer imgTxtDisplayer;

    public static QuestionFragment newInstance(String[] questions, String prompt) {
        QuestionFragment questionFragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putStringArray("questions", questions);
        args.putString("prompt", prompt);
        questionFragment.setArguments(args);
        return questionFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        questions = getArguments().getStringArray("questions");
        prompt = getArguments().getString("prompt");
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

        imgTxtDisplayer.initVariables(getContext(), getActivity());
        questionImageTab = imgTxtDisplayer.setImageTab(frameLayout);
        imgTxtDisplayer.setView(questionImageTab[0], tvQuestion, questions[0]);
        imgTxtDisplayer.initPreloadImages(questions, questionImageTab);
    }

    public void initOnRestart(String prompt){
        this.prompt = prompt;
        imgTxtDisplayer.setView(questionImageTab[0], tvQuestion, questions[0]);
        setPromptView();
        imgTxtDisplayer.initPreloadImages(questions, questionImageTab);
    }

    public void changeData(String currentAnswer, String answerToPreload, String prompt) {
        this.prompt = prompt;
        setPromptView();
        imgTxtDisplayer.changeData(currentAnswer, answerToPreload, tvQuestion, questionImageTab);
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

    public void setImgTxtDisplayer(ImageTextDisplayer imgTxtDisplayer) {
        this.imgTxtDisplayer = imgTxtDisplayer;
    }
}
