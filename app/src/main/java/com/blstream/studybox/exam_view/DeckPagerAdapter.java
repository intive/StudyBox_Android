package com.blstream.studybox.exam_view;


import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.blstream.studybox.R;
import com.blstream.studybox.exam_view.fragment.AnswerFragment;
import com.blstream.studybox.exam_view.fragment.QuestionFragment;
import com.blstream.studybox.model.database.Card;

import java.util.List;

public class DeckPagerAdapter extends FragmentPagerAdapter {

    private static final String TAG_CARDS_PROVIDER = "cardsProvider";
    private static final String TAG_IMAGE_TEXT_DISPLAY = "imageTextDisplay";
    private static final String TAG_INSTANCE_STATE = "instanceState";
    private static final int MAX_PRELOAD_IMAGE_COUNT = 4;
    private List<Card> flashcards;
    private QuestionFragment questionFragment;
    private AnswerFragment answerFragment;
    private ImageTextDisplay imgTxtDisplay;
    private CardsProvider cardsProvider;
    private int preloadImageCount;
    FragmentManager fragmentManager;

    public DeckPagerAdapter(FragmentManager fragmentManager, List<Card> flashcards,
                            int preImageCount, Activity activity) {
        super(fragmentManager);
        this.fragmentManager = fragmentManager;
        this.flashcards = flashcards;
        setPreloadImageCount(preImageCount);
        if (questionFragment == null) {
            imgTxtDisplay = new ImageTextDisplay(preloadImageCount, activity);
            cardsProvider = new CardsProvider(flashcards, preloadImageCount);
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(int page) {
        switch (page) {
            case 0:
                questionFragment = new QuestionFragment();
                questionFragment.setVariables(imgTxtDisplay, cardsProvider);
                return questionFragment;
            case 1:
                answerFragment = new AnswerFragment();
                answerFragment.setVariables(imgTxtDisplay, cardsProvider);
                return answerFragment;
            default:
                return null;
        }
    }

    private void setPreloadImageCount(int preImgCount) {
        if (preImgCount > MAX_PRELOAD_IMAGE_COUNT) {
            preloadImageCount = MAX_PRELOAD_IMAGE_COUNT;
        } else {
            preloadImageCount = preImgCount;
        }

        if (preloadImageCount > flashcards.size()) {
            preloadImageCount = flashcards.size();
        } else if (preloadImageCount == 0) {
            preloadImageCount = 1;
        }
    }

    public void changeData() {
        cardsProvider.changeCard();
        imgTxtDisplay.setImgIndexes(cardsProvider.getPosition());
        answerFragment.changeData();
        questionFragment.changeData();
    }

    public void onResultDisplay() {
        cardsProvider.initOnRestart();
        imgTxtDisplay.setImgIndexes(cardsProvider.getPosition());
        answerFragment.initOnRestart();
        questionFragment.initOnRestart();
    }

    public void changeFlashcards(List<Card> flashcards){
        this.flashcards = flashcards;
        setPreloadImageCount(preloadImageCount);
        cardsProvider.changeFlashcards(flashcards, preloadImageCount);
        imgTxtDisplay.setPreloadImageCount(preloadImageCount);
    }

    @Override
    public Parcelable saveState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(TAG_INSTANCE_STATE, super.saveState());
        bundle.putParcelable(TAG_CARDS_PROVIDER, cardsProvider);
        bundle.putParcelable(TAG_IMAGE_TEXT_DISPLAY, imgTxtDisplay);
        return bundle;
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader classLoader) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            answerFragment = (AnswerFragment) fragmentManager.findFragmentByTag(
                    "android:switcher:" + R.id.vpPager + ":" + 1);
            questionFragment = (QuestionFragment) fragmentManager.findFragmentByTag(
                    "android:switcher:" + R.id.vpPager + ":" + 0);
            cardsProvider = bundle.getParcelable(TAG_CARDS_PROVIDER);
            imgTxtDisplay = bundle.getParcelable(TAG_IMAGE_TEXT_DISPLAY);
            super.restoreState(bundle.getParcelable(TAG_INSTANCE_STATE), classLoader);
            return;
        }
        super.restoreState(state, classLoader);
    }
}
