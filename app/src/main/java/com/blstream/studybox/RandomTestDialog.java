package com.blstream.studybox;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.transition.Explode;
import android.transition.Transition;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blstream.studybox.activities.BaseExamActivity;

public class RandomTestDialog extends DialogFragment implements View.OnClickListener {

    private static final String TAG_DECK_ID = "deckId";
    private static final String TAG_DECK_NAME = "deckName";
    private static final String TAG_RANDOM_AMOUNT = "randomAmount";
    private static final String TAG_CARDS_AMOUNT = "cardsAmount";
    private static final String TAG_IN_EXAM = "inExam";

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
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        LinearLayout mainLayout = new LinearLayout(getActivity());
        mainLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        mainLayout.setLayoutParams(params);

        addViews(mainLayout);

        return mainLayout;
    }

    @Override
    public void onClick(View v) {
        dismiss();
        startExam(v);
    }

    private void addViews(LinearLayout mainLayout) {
        readArguments();

        mainLayout.addView(createTitle(getResources().getString(R.string.random_test_dialog_title)));
        mainLayout.addView(createTextView("1", cardsQuantity >= 1));
        mainLayout.addView(createDivider());

        for (int i = 1; i < 5; i++) {
            int number = i * 5;
            TextView amountView = createTextView(
                    String.valueOf(number),
                    cardsQuantity >= number);
            mainLayout.addView(amountView);
            mainLayout.addView(createDivider());
        }

        TextView allView = createTextView(getContext().getString(R.string.all), true);
        mainLayout.addView(allView);
    }

    private void readArguments() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            deckId = arguments.getString(TAG_DECK_ID);
            deckName = arguments.getString(TAG_DECK_NAME);
            cardsQuantity = arguments.getInt(TAG_CARDS_AMOUNT);
        }
    }

    private TextView createTitle(String text) {
        TextView titleView = new TextView(getContext());
        titleView.setText(text);
        titleView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorRaspberry));
        titleView.setTextColor(Color.WHITE);
        titleView.setTextSize(getResources().getDimension(R.dimen.random_dialog_title_text_size));
        titleView.setGravity(Gravity.CENTER);
        titleView.setMinimumHeight((int) getResources().getDimension(R.dimen.random_dialog_title_height));

        return titleView;
    }

    private TextView createTextView(String text, boolean isActive) {
        TextView textView = new TextView(getContext());
        textView.setText(text);
        textView.setOnClickListener(this);
        textView.setTextSize(getResources().getDimension(R.dimen.random_dialog_amount_text_size));
        textView.setGravity(Gravity.CENTER);
        textView.setMinimumHeight((int) getResources().getDimension(R.dimen.random_dialog_amount_view_height));

        if (isActive) {
            textView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorDarkBlue));
        } else {
            textView.setTextColor(Color.GRAY);
            textView.setEnabled(false);
        }

        return textView;
    }

    private View createDivider() {
        View divider = new View(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
        divider.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorDarkBlue));
        divider.setLayoutParams(layoutParams);

        return divider;
    }

    private void startExam(View view) {
        if (!(view instanceof TextView)) {
            return;
        }

        String randomAmount = ((TextView) view).getText().toString();
        randomAmount = convertNumberToWord(randomAmount);

        Context context = getContext();
        Intent intent = new Intent(context, BaseExamActivity.class);
        intent.putExtra(TAG_DECK_ID, deckId);
        intent.putExtra(TAG_DECK_NAME, deckName);
        intent.putExtra(TAG_RANDOM_AMOUNT, randomAmount);
        intent.putExtra(TAG_IN_EXAM, true);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setUpTransition();
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context);
            context.startActivity(intent, options.toBundle());
        } else {
            context.startActivity(intent);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setUpTransition() {
        Activity activity = (Activity) getContext();
        Transition exitTrans = new Explode();
        activity.getWindow().setExitTransition(exitTrans);
    }

    private String convertNumberToWord(String number) {
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
