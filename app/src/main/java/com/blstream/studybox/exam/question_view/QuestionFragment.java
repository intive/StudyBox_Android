package com.blstream.studybox.exam.question_view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

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
    private static final String TAG_QUESTION = "Pytanie";
    private static final String TAG_PROMPT = "Podpowiedź";

    private String cardId;
    private static boolean isPromptShowed;
    private int promptPosition;

    @Bind(R.id.question)
    TextView questionText;

    @Bind(R.id.question_image)
    ImageView questionImage;

    @Bind(R.id.viewSwitcher)
    ViewSwitcher viewSwitcher;

    @Bind(R.id.promptSwitcher)
    TextSwitcher promptSwitcher;

    @Bind(R.id.promptImageSwitcher)
    ImageSwitcher promptImageSwitcher;

    @Bind(R.id.promptQuestionSwitch)
    TextView promptQuestionSwitch;

    @Bind(R.id.prevPrompt)
    ImageButton prevPromptBtn;

    @Bind(R.id.nextPrompt)
    ImageButton nextPromptBtn;

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
        return new QuestionPresenter(getActivity().getApplicationContext());
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
        initView();
        return view;
    }

    private void initView() {
        questionText.setMovementMethod(new ScrollingMovementMethod());
        int padding = getResources().getDimensionPixelOffset(R.dimen.prompt_padding);
        initPromptSwitcher(padding);
        initPromptImageSwitcher(padding);
        setUpAnimations();
    }

    private void initPromptSwitcher(final int padding) {
        promptSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView textView = new TextView(getActivity());
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.MATCH_PARENT);
                params.gravity = Gravity.CENTER;
                textView.setLayoutParams(params);
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.exam_small_text));
                textView.setPadding(padding, padding, padding, padding);
                textView.setVerticalScrollBarEnabled(true);
                textView.setMovementMethod(new ScrollingMovementMethod());
                textView.setVerticalScrollBarEnabled(true);
                return textView;
            }
        });
    }

    private void initPromptImageSwitcher(final int padding) {
        promptImageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(getActivity());
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
                imageView.setLayoutParams(params);
                imageView.setPadding(padding, padding, padding, padding);
                return imageView;
            }
        });
    }

    private void setUpAnimations() {
        Activity activity = getActivity();
        viewSwitcher.setInAnimation(activity, R.anim.scale_up);
        viewSwitcher.setOutAnimation(activity, R.anim.scale_down);
        promptSwitcher.setInAnimation(activity, R.anim.fade_in);
        promptSwitcher.setOutAnimation(activity, R.anim.fade_out);
        promptImageSwitcher.setInAnimation(activity, R.anim.fade_in);
        promptImageSwitcher.setOutAnimation(activity, R.anim.fade_out);
    }

    @OnClick(R.id.promptQuestionSwitch)
    public void onPromptWithQuestionSwitch(View view) {
        presenter.switchView();
    }

    @Override
    public void switchToPrompts(){
        QuestionViewState questionViewState = (QuestionViewState<QuestionView>) viewState;
        questionViewState.setStateShowPrompt();

        viewSwitcher.showPrevious();
        promptQuestionSwitch.setText(TAG_QUESTION);
        promptQuestionSwitch.setBackgroundColor(getResources().getColor(R.color.colorDarkBlue));
        isPromptShowed = true;
        presenter.inPrompt(true);
    }

    @Override
    public void switchToQuestion(){
        QuestionViewState questionViewState = (QuestionViewState<QuestionView>) viewState;
        questionViewState.setStateShowQuestion();

        viewSwitcher.showNext();
        promptQuestionSwitch.setText(TAG_PROMPT);
        promptQuestionSwitch.setBackgroundColor(getResources().getColor(R.color.colorRaspberry));
        isPromptShowed = false;
        presenter.inPrompt(false);
    }

    @OnClick(R.id.nextPrompt)
    public void nextPrompt(View view) {
        promptPosition++;
        presenter.showPrompt(promptPosition);
    }

    @OnClick(R.id.prevPrompt)
    public void prevPrompt(View view) {
        promptPosition--;
        presenter.showPrompt(promptPosition);
    }

    @Override
    public void setPromptPosition(int promptPos) {
        promptPosition = promptPos;
    }

    @Override
    public void inPromptMode(boolean inPromptMode){
        isPromptShowed = inPromptMode;
        presenter.setView(isPromptShowed);
    }

    @Override
    public void showLeftPromptArrow(){
        QuestionViewState questionViewState = (QuestionViewState<QuestionView>) viewState;
        questionViewState.setStateShowLeftPromptArrow();

        prevPromptBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLeftPromptArrow(){
        QuestionViewState questionViewState = (QuestionViewState<QuestionView>) viewState;
        questionViewState.setStateHideLeftPromptArrow();

        prevPromptBtn.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showRightPromptArrow(){
        QuestionViewState questionViewState = (QuestionViewState<QuestionView>) viewState;
        questionViewState.setStateShowRightPromptArrow();

        nextPromptBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRightPromptArrow(){
        QuestionViewState questionViewState = (QuestionViewState<QuestionView>) viewState;
        questionViewState.setStateHideRightPromptArrow();

        nextPromptBtn.setVisibility(View.INVISIBLE);
    }

    @Override
    public void disablePrompt() {
        QuestionViewState questionViewState = (QuestionViewState<QuestionView>) viewState;
        questionViewState.setStateDisablePrompt();

        promptQuestionSwitch.setVisibility(View.GONE);
    }

    @Override
    public void enablePrompt() {
        QuestionViewState questionViewState = (QuestionViewState<QuestionView>) viewState;
        questionViewState.setStateEnablePrompt();

        promptQuestionSwitch.setVisibility(View.VISIBLE);
    }

     @OnClick({R.id.question, R.id.question_image})
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
        vs.setStateShowPromptText(prompt, promptPosition);

        promptImageSwitcher.setImageResource(android.R.color.transparent);
        promptSwitcher.setText(prompt);
    }

    @Override
    public void showImagePrompt(String url) {
        QuestionViewState vs = (QuestionViewState<QuestionView>) viewState;
        vs.setStateShowPromptImage(url, promptPosition);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        promptSwitcher.setText("");
        TipImageSwitcherPicasso imageSwitcherPicasso = new TipImageSwitcherPicasso(getActivity(), promptImageSwitcher);
        Picasso.with(getActivity()).load(url)
                .resize(displaymetrics.widthPixels, displaymetrics.heightPixels)
                .centerInside().placeholder(R.drawable.camera).into(imageSwitcherPicasso);
    }

    private void showImage(String url, ImageView image) {
        Activity activity = getActivity();
        Picasso.with(activity)
                .load(url)
                .fit()
                .centerInside()
                .placeholder(R.drawable.camera)
                .into(image);
    }
}
