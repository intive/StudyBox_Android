package com.blstream.studybox.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blstream.studybox.R;

public class AnswerFragment extends Fragment implements ChangeFragmentData {
    // Store instance variables
    private String title;
    TextView tvLabel;

    // newInstance constructor for creating fragment with arguments
    public static AnswerFragment newInstance(int page, String title) {
        AnswerFragment answerFragment = new AnswerFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        answerFragment.setArguments(args);
        return answerFragment;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getArguments().getString("someTitle");
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_answer, container, false);
        tvLabel = (TextView) view.findViewById(R.id.answer);
        tvLabel.setText(title);
        return view;
    }


    @Override
    public void changeData(String text) {
        tvLabel.setText(text);
    }
}
