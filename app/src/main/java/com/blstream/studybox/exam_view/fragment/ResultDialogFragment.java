package com.blstream.studybox.exam_view.fragment;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
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

public class ResultDialogFragment extends DialogFragment implements DialogInterface.OnShowListener{

    private static final String TAG_CORRECT_ANSWERS = "correctAnswers";
    private static final String TAG_NUMBER_OF_QUESTIONS = "noOfQuestions";

    @Bind(R.id.total_score)
    public TextView totalScore;

    private int correctAnswers;
    private int noOfQuestions;
    private Activity activity;

    public static ResultDialogFragment newInstance(int correctAnswers, int noOfQuestions) {
        ResultDialogFragment resultFragment = new ResultDialogFragment();
        Bundle args = new Bundle();
        args.putInt(TAG_CORRECT_ANSWERS, correctAnswers);
        args.putInt(TAG_NUMBER_OF_QUESTIONS, noOfQuestions);
        resultFragment.setArguments(args);
        return resultFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        correctAnswers = getArguments().getInt(TAG_CORRECT_ANSWERS);
        noOfQuestions = getArguments().getInt(TAG_NUMBER_OF_QUESTIONS);
        activity = getActivity();
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Light);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_result, container, false);
        initView(view);
        return view;
    }

    private void initView(View view){
        ButterKnife.bind(this, view);
        totalScore.setText(getString(R.string.correct_answers, correctAnswers, noOfQuestions));
    }

    @OnClick(R.id.improve_result)
    public void improveResult(View view) {
        dismiss();
    }

    @OnClick(R.id.my_decks)
    public void backToMyDecks(View view) {
        activity.finish();
    }

    @Override
    public void onShow(DialogInterface dialog) {
        ((OnResultShow) activity).onResultShow();
    }

    public interface OnResultShow {
        void onResultShow();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog result = new Dialog(activity, getTheme()) {
            @Override
            public void onBackPressed() {
                activity.finish();
                dismiss();
            }
        };
        result.setOnShowListener(this);
        return result;
    }
}
