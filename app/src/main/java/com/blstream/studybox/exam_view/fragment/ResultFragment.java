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

public class ResultFragment extends DialogFragment {

    @Bind(R.id.total_score)
    public TextView totalScore;

    @Bind(R.id.congrats)
    public TextView congrats;

    @Bind(R.id.improve_result)
    public TextView improve_result;

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
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Light);
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
        matchApiVersionLayout();
    }

    public void matchApiVersionLayout(){
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP){
            congrats.setPadding(0, getBarHeight("status_bar_height"), 0, 0);
            improve_result.setPadding(0, 0, 0, getBarHeight("navigation_bar_height"));
        }
    }

    public int getBarHeight(String barName) {
        int result = 0;
        int resourceId = getResources().getIdentifier(
                barName, "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new Dialog(getActivity(), getTheme()){
            @Override
            public void onBackPressed() {
                dismiss();
                getActivity().finish();
            }
        };
    }
}
