package com.blstream.studybox.exam_view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blstream.studybox.R;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AnswerFragment extends Fragment {

    @Bind(R.id.answer)
    public TextView tvAnswer;

    @Bind(R.id.answer_image)
    public ImageView answerImage;

    private String answer;

    public static AnswerFragment newInstance(String answer) {
        AnswerFragment answerFragment = new AnswerFragment();
        Bundle args = new Bundle();
        args.putString("answer", answer);
        answerFragment.setArguments(args);
        return answerFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        answer = getArguments().getString("answer");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_answer, container, false);
        initView(view);
        return view;
    }

    private void initView(View view){
        ButterKnife.bind(this, view);
        setAnswerView();
    }

    private void setAnswerView(){
        if (Patterns.WEB_URL.matcher(answer).matches()) {
            setAnswerVisibility(View.INVISIBLE, View.VISIBLE);
            Picasso.with(getActivity()).load(answer).fit().centerInside()
                    .placeholder(R.drawable.camera).into(answerImage);
        } else {
            tvAnswer.setText(answer);
            setAnswerVisibility(View.VISIBLE, View.INVISIBLE);
        }
    }

    private void setAnswerVisibility(int tvVisibility, int imgVisibility){
        tvAnswer.setVisibility(tvVisibility);
        answerImage.setVisibility(imgVisibility);
    }
}
