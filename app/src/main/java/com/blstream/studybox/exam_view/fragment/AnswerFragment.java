package com.blstream.studybox.exam_view.fragment;


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

public class AnswerFragment extends Fragment {

    @Bind(R.id.answer)
    public TextView tvAnswer;

    private String[] answers;
    private ImageView[] answerImageTab;
    private ImageTextDisplayer imgTxtDisplayer;

    public static AnswerFragment newInstance(String[] answers) {
        AnswerFragment answerFragment = new AnswerFragment();
        Bundle args = new Bundle();
        args.putStringArray("answers", answers);
        answerFragment.setArguments(args);
        return answerFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        answers = getArguments().getStringArray("answers");
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

        answerImageTab = imgTxtDisplayer.setImageTab(frameLayout);
        imgTxtDisplayer.setView(answerImageTab[0], tvAnswer, answers[0]);
        imgTxtDisplayer.initPreloadImages(answers, answerImageTab);
    }

    public void setImgTxtDisplayer(ImageTextDisplayer imgTxtDisplayer) {
        this.imgTxtDisplayer = imgTxtDisplayer;
    }

    public void initOnRestart(){
        imgTxtDisplayer.setView(answerImageTab[0], tvAnswer, answers[0]);
        imgTxtDisplayer.initPreloadImages(answers, answerImageTab);
    }

    public void changeData(String currentAnswer, String answerToPreload) {
        imgTxtDisplayer.changeData(currentAnswer, answerToPreload, tvAnswer, answerImageTab);
    }
}
