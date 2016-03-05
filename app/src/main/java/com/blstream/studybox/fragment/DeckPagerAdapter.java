package com.blstream.studybox.fragment;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.blstream.studybox.ExamActivity;

import java.util.List;

public class DeckPagerAdapter extends FragmentStatePagerAdapter {
    Fragment questionFragment;
    Fragment answerFragment;
    ChangeFragmentData questionDataUpdater;
    ChangeFragmentData answerDataUpdater;


    public DeckPagerAdapter(FragmentManager fragmentManager, List<ExamActivity.TestClass> questions) {
        super(fragmentManager);

        ExamActivity.TestClass data = questions.get(0);
        questionFragment = QuestionFragment.newInstance(0, data.question);
        questionDataUpdater = (ChangeFragmentData)questionFragment;

        answerFragment = AnswerFragment.newInstance(1, data.answer);
        answerDataUpdater = (ChangeFragmentData)answerFragment;
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return 2;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show first Fragment
                return questionFragment;
            case 1: // Fragment # 1 - This will show second Fragment
                return answerFragment;
            default:
                return null;
        }
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + position;
    }


    public ChangeFragmentData getAnswerDataUpdater() {
        return answerDataUpdater;
    }

    public ChangeFragmentData getQuestionDataUpdater() {
        return questionDataUpdater;
    }

}
