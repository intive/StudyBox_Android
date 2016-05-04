package com.blstream.studybox.exam.answer_view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blstream.studybox.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class StudyAnswerFragment extends AnswerFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_answer_study, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.skip_ans_btn)
    public void onSkipAnswerClick() {
        presenter.sendSkipAnswer();
    }
}
