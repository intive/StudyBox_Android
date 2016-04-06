package com.blstream.studybox.exam_view.fragment;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blstream.studybox.R;
import com.blstream.studybox.activities.DecksActivity;
import com.blstream.studybox.components.DrawerAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResultDialogFragment extends DialogFragment implements DialogInterface.OnShowListener{

    private static final String TAG_CORRECT_ANSWERS = "correctAnswers";
    private static final String TAG_NUMBER_OF_QUESTIONS = "noOfQuestions";

    @Bind(R.id.total_score)
    TextView totalScore;

    @Bind(R.id.toolbar_result)
    Toolbar toolbar;

    @Bind(R.id.nav_view_result)
    NavigationView navigationView;

    @Bind(R.id.drawer_layout_result)
    DrawerLayout drawerLayout;

    private int correctAnswers;
    private int noOfQuestions;

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
        setStyle(DialogFragment.STYLE_NORMAL, R.style.StudyBoxTheme);
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
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        DrawerAdapter drawerAdapter = new DrawerAdapter(getContext(), navigationView, drawerLayout, toolbar);
        drawerAdapter.attachDrawer();

        totalScore.setText(getString(R.string.correct_answers_format, correctAnswers, noOfQuestions));
    }

    @OnClick(R.id.improve_result)
    public void onClick(View view) {
        ((OnResultHide) getActivity()).onResultHide();
        dismiss();
    }

    @OnClick(R.id.my_decks)
    public void backToMyDecks(View view) {
        startActivity(new Intent(getContext(), DecksActivity.class));
    }

    @Override
    public void onShow(DialogInterface dialog) {
        ((OnResultShow) getActivity()).onResultShow();
    }

    public interface OnResultShow {
        void onResultShow();
    }

    public interface OnResultHide {
        void onResultHide();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog result = new Dialog(getActivity(), getTheme()) {
            @Override
            public void onBackPressed() {
                dismiss();
                getActivity().finish();
            }
        };
        result.setOnShowListener(this);
        return result;
    }
}
