package com.blstream.studybox;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Marek Macko on 27.04.2016.
 */
public class RandomTestDialog extends DialogFragment implements View.OnClickListener {

    private static final int TEXT_SIZE = 25;

    @Bind(R.id.random_test_main)
    LinearLayout mainLayout;

    public RandomTestDialog() {
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

        getDialog().setTitle("Podaj ilość fiszek w teście");

        addTextViews();
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(v.getContext(), ((TextView)v).getText(), Toast.LENGTH_SHORT).show();
    }

    private void addTextViews() {
        TextView firstTextView = createTextView("1");
        mainLayout.addView(firstTextView);
        for (int i = 1; i < 5; i++) {
            TextView textView = createTextView("" + i * 5);
            mainLayout.addView(textView);
        }

        TextView textView = createTextView("Wszystkie");
        mainLayout.addView(textView);
    }

    private TextView createTextView(String text) {
        TextView textView = new TextView(getContext());
        textView.setText(text);
        textView.setOnClickListener(this);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TEXT_SIZE);

        return textView;
    }
}
