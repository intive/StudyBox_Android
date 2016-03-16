package com.blstream.studybox.exam_view;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.blstream.studybox.ExamActivity;
import com.blstream.studybox.exam_view.fragment.AnswerFragment;
import com.blstream.studybox.exam_view.fragment.QuestionFragment;
import com.blstream.studybox.exam_view.fragment.ResultFragment;

public class DeckPagerAdapter extends FragmentStatePagerAdapter {

    private final ExamActivity.Deck deck;
    private int correctAnswers;
    private final ResultFragment resultFragment;

    public DeckPagerAdapter(FragmentManager fragmentManager, ExamActivity.Deck deck) {
        super(fragmentManager);
        this.deck = deck;
        resultFragment = ResultFragment.newInstance(correctAnswers, deck.numberOfQuestions);
    }

    @Override
    public int getCount() {
        return deck.numberOfQuestions * 2 + 1;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == getCount() - 1)
            return resultFragment;
        ExamActivity.Card card = deck.cards.get(position / 2);
        switch (position % 2) {
            case 0:
                return QuestionFragment.newInstance(card.question, card.prompt);
            case 1:
                return AnswerFragment.newInstance(card.answer);
            default:
                return null;
        }
    }

    public void setTotalScore(int correctAnswers) {
        resultFragment.setTotalScore(correctAnswers);
        Log.d("correctAnswers", String.valueOf(correctAnswers));
    }
}
