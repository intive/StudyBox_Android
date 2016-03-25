package com.blstream.studybox.exam_view;


import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.blstream.studybox.activities.ExamActivity;
import com.blstream.studybox.exam_view.fragment.AnswerFragment;
import com.blstream.studybox.exam_view.fragment.QuestionFragment;

public class DeckPagerAdapter extends FragmentStatePagerAdapter {

    private static final int MAX_PRELOAD_IMAGE_COUNT = 4;
    private final ExamActivity.Deck deck;
    private final QuestionFragment questionFragment;
    private final AnswerFragment answerFragment;
    private int preloadImageCount;
    ImageTextDisplayer imgTxtDisplayer;
    CardsProvider cardsProvider;

    public DeckPagerAdapter(FragmentManager fragmentManager, ExamActivity.Deck deck,
                            int preloadImageCount, Activity activity) {
        super(fragmentManager);
        this.deck = deck;
        setPreloadImageCount(preloadImageCount);
        imgTxtDisplayer = new ImageTextDisplayer(preloadImageCount, activity);
        cardsProvider = new CardsProvider(deck, preloadImageCount);

        questionFragment = new QuestionFragment();
        answerFragment = new AnswerFragment();
        questionFragment.setVariables(imgTxtDisplayer, cardsProvider);
        answerFragment.setVariables(imgTxtDisplayer, cardsProvider);
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

    public void setPreloadImageCount(int preImgCount) {
        if(preloadImageCount > MAX_PRELOAD_IMAGE_COUNT)
            preloadImageCount = MAX_PRELOAD_IMAGE_COUNT;
        else
            preloadImageCount = preImgCount;

        if(preloadImageCount > deck.numberOfQuestions)
            preloadImageCount = deck.numberOfQuestions;
        else if(preloadImageCount == 0)
            preloadImageCount = 1;
    }

    public void changeData(){
        cardsProvider.changeCard();
        imgTxtDisplayer.setImgIndexes(cardsProvider.getPosition());
        answerFragment.changeData();
        questionFragment.changeData();
    }

    public void onResultDisplay(){
        cardsProvider.initOnRestart();
        imgTxtDisplayer.setImgIndexes(cardsProvider.getPosition());
        answerFragment.initOnRestart();
        questionFragment.initOnRestart();
    }
}
