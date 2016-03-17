package com.blstream.studybox.exam_view.fragment;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blstream.studybox.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ResultFragment extends Fragment {

    @Bind(R.id.total_score)
    public TextView totalScore;

    private int correctAnswers;
    private int noOfQuestions;

    public static ResultFragment newInstance(int correctAnswers, int noOfQuestions) {
        ResultFragment resultFragment = new ResultFragment();
        Bundle args = new Bundle();
        args.putInt("correctAnswers", correctAnswers);
        args.putInt("noOfQuestions", noOfQuestions);
        resultFragment.setArguments(args);
        return resultFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        correctAnswers = getArguments().getInt("correctAnswers");
        noOfQuestions = getArguments().getInt("noOfQuestions");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);
        initView(view);
        return view;
    }

    public void initView(View view){
        ButterKnife.bind(this, view);
        totalScore.setText(getString(R.string.correct_answers, correctAnswers, noOfQuestions));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        populateViewForOrientation(inflater, (ViewGroup) getView());
    }

    private void populateViewForOrientation(LayoutInflater inflater, ViewGroup viewGroup) {
        viewGroup.removeAllViewsInLayout();
        View subview = inflater.inflate(R.layout.fragment_result, viewGroup);
        initView(subview);
    }

    public void setTotalScore(int correctAnswers){
        this.correctAnswers = correctAnswers;
        totalScore.setText(getString(R.string.correct_answers, correctAnswers, noOfQuestions));
    }
}
