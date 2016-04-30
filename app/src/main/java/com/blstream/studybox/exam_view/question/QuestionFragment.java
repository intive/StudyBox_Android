package com.blstream.studybox.exam_view.question;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blstream.studybox.R;
import com.hannesdorfmann.mosby.mvp.viewstate.MvpViewStateFragment;
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class QuestionFragment extends MvpViewStateFragment<QuestionView, QuestionPresenter>
        implements QuestionView {

    private static final String TAG_CARD_ID = "cardId";

    private String cardId;

    @Bind(R.id.question)
    TextView questionText;

    @Bind(R.id.prompt)
    TextView promptText;

    @Bind(R.id.question_image)
    ImageView questionImage;

    @Bind(R.id.prompt_image)
    ImageView promptImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        Bundle args = getArguments();
        if (args != null) {
            cardId = args.getString(TAG_CARD_ID);
        }
    }

    @NonNull
    @Override
    public QuestionPresenter createPresenter() {
        return new QuestionPresenter();
    }

    @NonNull
    @Override
    public ViewState<QuestionView> createViewState() {
        return new QuestionViewState<>();
    }

    @Override
    public void onNewViewStateInstance() {
        presenter.loadQuestion(cardId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void disablePrompt() {
        QuestionViewState vs = (QuestionViewState<QuestionView>) viewState;
        vs.setStateDisablePrompt();

        promptText.setTextColor(Color.GRAY);
        promptText.setClickable(false);
    }

    @Override
    public void enablePrompt() {
        QuestionViewState vs = (QuestionViewState<QuestionView>) viewState;
        vs.setStateEnablePrompt();

        promptText.setClickable(true);
        promptText.setTextColor(Color.WHITE);
    }

    @OnClick (R.id.prompt)
    public void onPromptClick(View view) {
        presenter.showPrompt();
        promptText.setClickable(false);
    }

    @OnClick(R.id.question)
    public void onQuestionClick() {
        presenter.showAnswer();
    }

    @Override
    public void showTextQuestion(String question) {
        QuestionViewState vs = (QuestionViewState<QuestionView>) viewState;
        vs.setStateShowQuestionText(question);

        questionText.setVisibility(View.VISIBLE);
        questionImage.setVisibility(View.GONE);
        questionText.setText(question);
    }

    @Override
    public void showImageQuestion(String url) {
        QuestionViewState vs = (QuestionViewState<QuestionView>) viewState;
        vs.setStateShowQuestionImage(url);

        questionText.setVisibility(View.GONE);
        questionImage.setVisibility(View.VISIBLE);
        showImage(url, questionImage);
    }

    @Override
    public void showTextPrompt(String prompt) {
        QuestionViewState vs = (QuestionViewState<QuestionView>) viewState;
        vs.setStateShowPromptText(prompt);

        promptText.setVisibility(View.VISIBLE);
        promptImage.setVisibility(View.GONE);
        promptText.setText(prompt);
    }

    @Override
    public void showImagePrompt(String url) {
        QuestionViewState vs = (QuestionViewState<QuestionView>) viewState;
        vs.setStateShowPromptImage(url);

        promptText.setVisibility(View.GONE);
        promptImage.setVisibility(View.VISIBLE);
        showImage(url, promptImage);
    }

    private void showImage(String url, ImageView image) {
        Activity activity = getActivity();
        Picasso.with(activity)
                .load(url)
                .placeholder(R.drawable.camera)
                .into(image);
    }
}
