package com.blstream.studybox.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.blstream.studybox.R;
import com.blstream.studybox.exam_view.DeckPagerAdapter;
import com.blstream.studybox.exam_view.fragment.AnswerFragment;
import com.blstream.studybox.exam_view.fragment.ResultDialogFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ExamActivity extends AppCompatActivity implements AnswerFragment.OnMoveToNextCard, ResultDialogFragment.OnRestartExam {

    @Bind(R.id.deckName)
    public TextView deckName;

    @Bind(R.id.questionNo)
    public TextView questionNo;

    @Bind(R.id.correctAnswers)
    public TextView correctAnswers;

    @Bind(R.id.vpPager)
    public ViewPager viewPager;

    private DeckPagerAdapter adapterViewPager;
    private int cardCounter;
    private int correctAnswersCounter;
    private Integer noOfQuestions;
    ResultDialogFragment resultDialog;

    //------For testing------
    final List<Card> questions = new ArrayList<>();
    private Deck deck;
    //------For testing------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        //------For testing------
        populateDeck();
        //------For testing------

        setVariables();
        initView();
    }

    public void initView() {
        ButterKnife.bind(this);
        deckName.setText(deck.deckName);
        questionNo.setText(getString(R.string.question_no, cardCounter));
        correctAnswers.setText(getString(
                R.string.correct_answers, correctAnswersCounter, noOfQuestions));
        adapterViewPager =
                new DeckPagerAdapter(getSupportFragmentManager(), deck, 4, this);
        viewPager.setAdapter(adapterViewPager);
    }

    public void setVariables(){
        noOfQuestions = deck.numberOfQuestions;
        cardCounter = 1;
    }

    public void updateCard(boolean addCorrectAnswer){
        updateCounters(addCorrectAnswer);
        setCard();
    }

    public void setCard(){
        if(cardCounter - 1  == noOfQuestions)
            displayResult();
        else
            displayNextCard();
    }

    public void displayNextCard() {
        viewPager.setCurrentItem(0, false);
        adapterViewPager.changeData();
        questionNo.setText(getString(R.string.question_no, cardCounter));
        correctAnswers.setText(getString(
                R.string.correct_answers, correctAnswersCounter, noOfQuestions));
    }

    public void displayResult(){
        resultDialog = ResultDialogFragment.newInstance(
                correctAnswersCounter, deck.numberOfQuestions);
        resultDialog.show(getSupportFragmentManager(), "result");
        viewPager.setVisibility(View.INVISIBLE);
    }

    public void restarExam(){
        setInitialValues();
        adapterViewPager.onResultDisplay();
        setFirstCard();
        resultDialog.dismiss();
    }

    public void updateCounters(boolean addCorrectAnswer){
        updateCorrectAnswersCounter(addCorrectAnswer);
        cardCounter++;
    }

    public void updateCorrectAnswersCounter(boolean addCorrectAnswer){
        if(addCorrectAnswer)
            correctAnswersCounter++;
    }

    public void setInitialValues(){
        cardCounter = 1;
        correctAnswersCounter = 0;
    }

    public void setFirstCard() {
        questionNo.setText(getString(R.string.question_no, 1));
        correctAnswers.setText(getString(
                R.string.correct_answers, correctAnswersCounter, noOfQuestions));
        viewPager.setCurrentItem(0, false);
        viewPager.setVisibility(View.VISIBLE);
    }


    //---------------------------

    @Override
    public void onMoveToNextCard(boolean addCorrectAnswer) {
        updateCard(addCorrectAnswer);
    }

    @Override
    public void onRestartExam(){
        restarExam();
    }

    //------For testing------
    public class Card{
        public final String question;
        public final String answer;
        public final String prompt;

        public Card(String que, String prmt, String ans){
            question = que;
            answer = ans;
            prompt = prmt;
        }
    }

    public class Deck{
        public final Integer deckNumber;
        public final String deckName;
        public final int numberOfQuestions;
        public final List<Card> cards;

        public Deck(int dNo, String dName, Integer nOfQue, List<Card> crds){
            deckNumber = dNo;
            deckName = dName;
            numberOfQuestions = nOfQue;
            cards = crds;
        }
    }

    public void populateDeck(){
        questions.add(new Card("Pytanie", "Dobra podpowiedz", "Odpowiedz"));
        questions.add(new Card("What gets wet with drying?", "", "http://animaliaz-life.com/data_images/horse/horse6.jpg"));
        questions.add(new Card("http://i.telegraph.co.uk/multimedia/archive/02540/qi_2540330c.jpg", "Dobra podpowiedź", "http://i.telegraph.co.uk/multimedia/archive/02540/qi_2540330c.jpg"));
        questions.add(new Card("What gets wet with drying?", "Bug? That's not a bug, that's a feature.", "A towel"));
        questions.add(new Card("Nastepne pytanie", "Podpowiedz 1", "https://upload.wikimedia.org/wikipedia/commons/a/a5/" +
                "European_Rabbit,_Lake_District,_UK_-_August_2011.jpg"));
        questions.add(new Card("Nastepne pytanie", "Podpowiedz 1", "https://encrypted-tbn2.gstatic.com/" +
                "images?q=tbn:ANd9GcSQsyEbYgkjylaK0Mym8I7ER295ecK3QXIWtHVba6pI43QFUjLf"));
        questions.add(new Card("Nastepne pytanie", "Podpowiedz 1", "https://fishfair2000.files.wordpress.com/2015/01/rabbits.jpg"));
        deck = new Deck(3, "Biologia", 7, questions);
    }
    //------For testing------
}
