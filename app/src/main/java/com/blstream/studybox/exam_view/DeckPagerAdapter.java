package com.blstream.studybox.exam_view;


import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.blstream.studybox.exam_view.fragment.AnswerFragment;
import com.blstream.studybox.exam_view.fragment.QuestionFragment;
import com.blstream.studybox.model.Deck;

public class DeckPagerAdapter extends FragmentStatePagerAdapter {

    private static final int MAX_PRELOAD_IMAGE_COUNT = 4;
    private final Deck deck;
    private final QuestionFragment questionFragment;
    private final AnswerFragment answerFragment;
    private final ImageTextDisplay imgTxtDisplay;
    private final CardsProvider cardsProvider;
    private int preloadImageCount;

    public DeckPagerAdapter(FragmentManager fragmentManager, Deck deck,
                            int preImagCount, Activity activity) {
        super(fragmentManager);
        this.deck = deck;
        setPreloadImageCount(preImagCount);
        imgTxtDisplay = new ImageTextDisplay(preloadImageCount, activity);
        cardsProvider = new CardsProvider(deck, preloadImageCount);

        questionFragment = new QuestionFragment();
        answerFragment = new AnswerFragment();
        questionFragment.setVariables(imgTxtDisplay, cardsProvider);
        answerFragment.setVariables(imgTxtDisplay, cardsProvider);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(int page) {
        switch (page) {
            case 0:
                return questionFragment;
            case 1:
                return answerFragment;
            default:
                return null;
        }
    }

    private void setPreloadImageCount(int preImgCount) {
        if(preImgCount > MAX_PRELOAD_IMAGE_COUNT)
            preloadImageCount = MAX_PRELOAD_IMAGE_COUNT;
        else
            preloadImageCount = preImgCount;

        if(preloadImageCount > deck.getNoOfQuestions())
            preloadImageCount = deck.getNoOfQuestions();
        else if(preloadImageCount == 0)
            preloadImageCount = 1;
    }

    public void changeData(){
        cardsProvider.changeCard();
        imgTxtDisplay.setImgIndexes(cardsProvider.getPosition());
        answerFragment.changeData();
        questionFragment.changeData();
    }

    public void onResultDisplay(){
        cardsProvider.initOnRestart();
        imgTxtDisplay.setImgIndexes(cardsProvider.getPosition());
        answerFragment.initOnRestart();
        questionFragment.initOnRestart();
    }
}
