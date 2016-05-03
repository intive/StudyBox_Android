package com.blstream.studybox;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blstream.studybox.activities.ExamActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Marek Macko on 27.04.2016.
 *
 */
public class RandomTestDialog extends DialogFragment implements View.OnClickListener {

    private static final String TAG_DECK_ID = "deckId";
    private static final String TAG_DECK_NAME = "deckName";
    private static final String TAG_RANDOM_AMOUNT = "randomAmount";
    private static final String TAG_CARDS_AMOUNT = "cardsAmount";
    private static final int TEXT_SIZE = 20;

    @Bind(R.id.random_test_main)
    LinearLayout mainLayout;

    private String deckId;
    private String deckName;
    private int cardsQuantity;

    public static RandomTestDialog newInstance(String deckId, String deckName, int cardsAmount) {
        Bundle arguments = new Bundle();
        arguments.putString(TAG_DECK_ID, deckId);
        arguments.putString(TAG_DECK_NAME, deckName);
        arguments.putInt(TAG_CARDS_AMOUNT, cardsAmount);

        RandomTestDialog instance = new RandomTestDialog();
        instance.setArguments(arguments);

        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.random_test , container);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle arguments = getArguments();
        if (arguments != null) {
            deckId = arguments.getString(TAG_DECK_ID);
            deckName = arguments.getString(TAG_DECK_NAME);
            cardsQuantity = arguments.getInt(TAG_CARDS_AMOUNT);
        }

        getDialog().setTitle(R.string.random_test_dialog_title);
        addTextViews();
    }

    @Override
    public void onClick(View v) {
        dismiss();
        startExam(v);
    }

    private void addTextViews() {
        TextView firstTextView = createTextView("1", cardsQuantity >= 1);
        mainLayout.addView(firstTextView);

        for (int i = 1; i < 5; i++) {
            int number = i * 5;
            TextView textView = createTextView(
                    String.valueOf(number),
                    cardsQuantity >= number);
            mainLayout.addView(textView);
        }

        TextView textView = createTextView(
                getContext().getString(R.string.all), true);
        mainLayout.addView(textView);
    }

    private TextView createTextView(String text, boolean isActive) {
        TextView textView = new TextView(getContext());
        textView.setText(text);
        textView.setOnClickListener(this);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TEXT_SIZE);

        if (isActive) {
            textView.setTextColor(Color.BLACK);
        } else {
            textView.setTextColor(Color.GRAY);
            textView.setEnabled(false);
        }

        return textView;
    }

    private void startExam(View view) {
        if (!(view instanceof TextView)) {
            return;
        }

        String randomAmount = ((TextView) view).getText().toString();
        randomAmount = convertToWord(randomAmount);

        Context context = getContext();
        Intent intent = new Intent(context, ExamActivity.class);
        intent.putExtra(TAG_DECK_ID, deckId);
        intent.putExtra(TAG_DECK_NAME, deckName);
        intent.putExtra(TAG_RANDOM_AMOUNT, randomAmount);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            context.startActivity(intent,
                    ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context).toBundle());
        } else {
            context.startActivity(intent);
        }
    }

    private String convertToWord(String number) {
        switch (number) {
            case "1":
                return "one";
            case "5":
                return "five";
            case "10":
                return "ten";
            case "15":
                return "fifteen";
            case "20":
                return "twenty";
            default:
                return null;
        }
    }
}
