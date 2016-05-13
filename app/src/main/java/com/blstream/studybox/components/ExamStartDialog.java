package com.blstream.studybox.components;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.DialogFragment;
import android.transition.Explode;
import android.transition.Transition;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.blstream.studybox.R;
import com.blstream.studybox.activities.BaseExamActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExamStartDialog extends DialogFragment {

    private static final String TAG_DECK_ID = "deckId";
    private static final String TAG_DECK_NAME = "deckName";
    private static final String TAG_RANDOM_AMOUNT = "randomAmount";
    private static final String TAG_CARDS_AMOUNT = "cardsAmount";
    private static final String TAG_IN_EXAM = "inExam";

    private String deckId;
    private String deckName;
    private int cardsQuantity;

    @Bind(R.id.exam_mode_button)
    Button exam;
    @Bind({ R.id.mode_1_button, R.id.mode_5_button, R.id.mode_10_button, R.id.mode_15_button, R.id.mode_20_button, R.id.mode_all_button })
    Button[] quantityButton;

    public static ExamStartDialog newInstance(String deckId, String deckName, int cardsAmount) {
        Bundle arguments = new Bundle();
        arguments.putString(TAG_DECK_ID, deckId);
        arguments.putString(TAG_DECK_NAME, deckName);
        arguments.putInt(TAG_CARDS_AMOUNT, cardsAmount);

        ExamStartDialog instance = new ExamStartDialog();
        instance.setArguments(arguments);
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_mode, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.study_mode_button)
    public void startStudy(View view) {
        readArguments();
        dismiss();
        setUpTest(null, false);
    }

    @OnClick(R.id.exam_mode_button)
    public void startExam(View view) {
        readArguments();
        enableButtons(cardsQuantity);
    }

    @OnClick({R.id.mode_1_button, R.id.mode_5_button, R.id.mode_10_button, R.id.mode_15_button, R.id.mode_20_button, R.id.mode_all_button})
    public void setQuantity(View view) {
        dismiss();
        setUpTest(view, true);
    }

    private void enableButtons(int cardsQuantity) {
        exam.setBackgroundResource(R.drawable.background_raspberry);
        quantityButton[5].setEnabled(true);
        quantityButton[0].setEnabled(true);
        if (cardsQuantity > 5) quantityButton[1].setEnabled(true);
        if (cardsQuantity > 10) quantityButton[2].setEnabled(true);
        if (cardsQuantity > 15) quantityButton[3].setEnabled(true);
        if (cardsQuantity > 20) quantityButton[4].setEnabled(true);
    }

    private void readArguments() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            deckId = arguments.getString(TAG_DECK_ID);
            deckName = arguments.getString(TAG_DECK_NAME);
            cardsQuantity = arguments.getInt(TAG_CARDS_AMOUNT);
        }
    }

    private void setUpTest(View view, boolean isExam) {
        Context context = getContext();
        Intent intent = new Intent(context, BaseExamActivity.class);
        intent.putExtra(TAG_DECK_ID, deckId);
        intent.putExtra(TAG_DECK_NAME, deckName);
        intent.putExtra(TAG_IN_EXAM, isExam);
        if(isExam) {
            String randomAmount = convertNumberToWord(view);
            intent.putExtra(TAG_RANDOM_AMOUNT, randomAmount);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setUpTransition();
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context);
            context.startActivity(intent, options.toBundle());
        } else {
            context.startActivity(intent);
        }
    }

    private String convertNumberToWord(View view) {
        switch (view.getId()) {
            case R.id.mode_1_button:
                return "one";
            case R.id.mode_5_button:
                return "five";
            case R.id.mode_10_button:
                return "ten";
            case R.id.mode_15_button:
                return "fifteen";
            case R.id.mode_20_button:
                return "twenty";
            case R.id.mode_all_button:
                return null;
            default:
                return null;
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setUpTransition() {
        Activity activity = (Activity) getContext();
        Transition exitTrans = new Explode();
        activity.getWindow().setExitTransition(exitTrans);
    }
}
