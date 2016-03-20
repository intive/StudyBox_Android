package com.blstream.studybox.exam_view;


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
    private int counter;
    private int position;
    private int preloadImageCount;
    private ImageTextDisplayer imgTxtDisplayer;

    public DeckPagerAdapter(FragmentManager fragmentManager, ExamActivity.Deck deck, int preloadImageCount) {
        super(fragmentManager);
        this.deck = deck;
        setPreloadImageCount(preloadImageCount);
        String[] answer = new String[preloadImageCount];
        String[] question = new String[preloadImageCount];
        String prompt = deck.cards.get(0).prompt;
        for(int i = 0; i < preloadImageCount; i++){
            answer[i] = deck.cards.get(i).answer;
            question[i] = deck.cards.get(i).question;
        }
        imgTxtDisplayer = new ImageTextDisplayer(preloadImageCount);

        questionFragment = QuestionFragment.newInstance(question, prompt);
        questionFragment.setImgTxtDisplayer(imgTxtDisplayer);
        answerFragment = AnswerFragment.newInstance(answer);
        answerFragment.setImgTxtDisplayer(imgTxtDisplayer);
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

    public void updateCounters(){
        position++;
        if(counter != preloadImageCount - 1)
            counter++;
        else
            counter = 0;
    }

    public void changeData(){
        ExamActivity.Card currentCard = deck.cards.get(position);
        imgTxtDisplayer.setImgIndexes(counter);
        if(deck.numberOfQuestions > position + preloadImageCount - 1) {
            ExamActivity.Card card = deck.cards.get(position + preloadImageCount - 1);
            answerFragment.changeData(currentCard.answer, card.answer);
            questionFragment.changeData(currentCard.question, card.question, currentCard.prompt);
        } else {
            answerFragment.changeData(currentCard.answer, "");
            questionFragment.changeData(currentCard.question, "", currentCard.prompt);
        }
    }

    public void setPreloadImageCount(int preloadImageCount) {
        if(preloadImageCount > MAX_PRELOAD_IMAGE_COUNT)
            this.preloadImageCount = MAX_PRELOAD_IMAGE_COUNT;
        else
            this.preloadImageCount = preloadImageCount;

        if(this.preloadImageCount > deck.numberOfQuestions)
            this.preloadImageCount = deck.numberOfQuestions;
    }

    public void initOnRestart(){
        position = 0;
        counter = 0;
        questionFragment.initOnRestart(deck.cards.get(0).prompt);
        answerFragment.initOnRestart();
    }
}
