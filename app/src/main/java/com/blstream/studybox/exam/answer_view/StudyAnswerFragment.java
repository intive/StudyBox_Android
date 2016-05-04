package com.blstream.studybox.exam.answer_view;

import android.app.Activity;
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

/**
 * Created by Oldzi on 02.05.2016.
 */
public class StudyAnswerFragment extends MvpViewStateFragment<AnswerView, AnswerPresenter>
        implements AnswerView {

    private static final String TAG_CARD_ID = "cardId";
    private String cardId;

    @Bind(R.id.answer_study)
    TextView answerText;

    @Bind(R.id.answer_image_study)
    ImageView answerImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_answer_study, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.skip_ans_btn_study)
    public void onSkipAnswerClick() {
        presenter.sendSkipAnswer();
    }

    @OnClick(R.id.correct_ans_btn_study)
    public void onCorrectAnswerClick() {
        presenter.sendCorrectAnswer();
    }

    @OnClick(R.id.incorrect_ans_btn_study)
    public void onWrongAnswerClick() {
        presenter.sendWrongAnswer();
    }

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
    public AnswerPresenter createPresenter() {
        return new AnswerPresenter();
    }

    @NonNull
    @Override
    public ViewState<AnswerView> createViewState() {
        return new AnswerViewState<>();
    }

    @Override
    public void onNewViewStateInstance() {
        presenter.loadAnswer(cardId);
    }

    @Override
    public void showTextAnswer(String answer) {
        AnswerViewState vs = (AnswerViewState<AnswerView>) viewState;
        vs.setStateShowAnswerText(answer);

        answerImage.setVisibility(View.GONE);
        answerText.setVisibility(View.VISIBLE);
        answerText.setText(answer);
    }

    @Override
    public void showImageAnswer(String url) {
        AnswerViewState vs = (AnswerViewState<AnswerView>) viewState;
        vs.setStateShowAnswerImage(url);

        answerText.setVisibility(View.GONE);
        answerImage.setVisibility(View.VISIBLE);
        showImage(url, answerImage);
    }

    private void showImage(String url, ImageView image) {
        Activity activity = getActivity();
        Picasso.with(activity)
                .load(url)
                .placeholder(R.drawable.camera)
                .into(image);
    }
}
