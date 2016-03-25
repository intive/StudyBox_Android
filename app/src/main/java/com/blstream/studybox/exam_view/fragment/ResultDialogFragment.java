package com.blstream.studybox.exam_view.fragment;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blstream.studybox.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResultDialogFragment extends DialogFragment {

    @Bind(R.id.total_score)
    public TextView totalScore;

    private int correctAnswers;
    private int noOfQuestions;

    public static ResultDialogFragment newInstance(int correctAnswers, int noOfQuestions) {
        ResultDialogFragment resultFragment = new ResultDialogFragment();
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
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Light);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_result, container, false);
        initView(view);
        return view;
    }

    public void initView(View view){
        ButterKnife.bind(this, view);
        totalScore.setText(getString(R.string.correct_answers, correctAnswers, noOfQuestions));
    }

    @OnClick(R.id.improve_result)
    public void onClick(View view) {
        if(((OnRestartExam) getActivity()).onRestartExam())
            dismiss();
    }

    public interface OnRestartExam {
        boolean onRestartExam();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new Dialog(getActivity(), getTheme()) {
            @Override
            public void onBackPressed() {
                dismiss();
                getActivity().finish();
            }
        };
    }
}
