package com.blstream.studybox.exam_view.fragment;

import android.graphics.Color;
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
import butterknife.OnClick;


public class QuestionFragment extends Fragment {

    @Bind(R.id.question)
    public TextView tvQuestion;

    @Bind(R.id.prompt)
    public TextView tvPrompt;
    
    @Bind(R.id.question_image)
    public ImageView questionImage;

    private String question;
    private String prompt;

    public static QuestionFragment newInstance(String question, String prompt) {
        QuestionFragment questionFragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putString("question", question);
        args.putString("prompt", prompt);
        questionFragment.setArguments(args);
        return questionFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        question = getArguments().getString("question");
        prompt = getArguments().getString("prompt");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);
        initView(view);
        return view;
    }

    public void initView(View view) {
        ButterKnife.bind(this, view);
        setQuestionView();
        setPromptView();
    }

    public void setQuestionView() {
        if (Patterns.WEB_URL.matcher(question).matches()) {
            setQuestionVisibility(View.INVISIBLE, View.VISIBLE);
            Picasso.with(getActivity()).load(question).fit().centerInside()
                    .placeholder(R.drawable.camera).into(questionImage);
        } else {
            tvQuestion.setText(question);
            setQuestionVisibility(View.VISIBLE, View.INVISIBLE);
        }
    }

    public void setQuestionVisibility(int tvVisibility, int imgVisibility){
        tvQuestion.setVisibility(tvVisibility);
        questionImage.setVisibility(imgVisibility);
    }

    public void setPromptView() {
        if(prompt.equals(""))
            tvPrompt.setTextColor(Color.GRAY);
        else
            tvPrompt.setClickable(true);
    }

    @OnClick (R.id.prompt)
        public void onClick(View view) {
            tvPrompt.setText(prompt);
            tvPrompt.setClickable(false);
        }
}
