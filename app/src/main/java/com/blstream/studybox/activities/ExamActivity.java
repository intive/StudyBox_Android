package com.blstream.studybox.activities;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;

import com.blstream.studybox.ConnectionStatusReceiver;
import com.blstream.studybox.Constants;
import com.blstream.studybox.R;
import com.blstream.studybox.components.DrawerAdapter;
import com.blstream.studybox.exam_view.DeckPagerAdapter;
import com.blstream.studybox.exam_view.fragment.AnswerFragment;
import com.blstream.studybox.exam_view.fragment.ResultDialogFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ExamActivity extends AppCompatActivity implements AnswerFragment.OnMoveToNextCard, ResultDialogFragment.OnResultShow {

    private ConnectionStatusReceiver connectionStatusReceiver = new ConnectionStatusReceiver();

    @Bind(R.id.deckName)
    public TextView deckName;

    @Bind(R.id.questionNo)
    public TextView questionNo;

    @Bind(R.id.correctAnswers)
    public TextView correctAnswers;

    @Bind(R.id.vpPager)
    public ViewPager viewPager;

    @Bind(R.id.toolbar_exam)
    Toolbar toolbar;

    @Bind(R.id.nav_view_exam)
    NavigationView navigationView;

    @Bind(R.id.drawer_layout_exam)
    DrawerLayout drawerLayout;

    private DeckPagerAdapter adapterViewPager;
    private int cardCounter;
    private int correctAnswersCounter;
    private Integer noOfQuestions;
    DrawerAdapter drawerAdapter;

    //------For testing------
    private final List<Card> questions = new ArrayList<>();
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
    @Override
    protected void onResume(){
        super.onResume();
        registerReceiver(connectionStatusReceiver, ConnectionStatusReceiver.filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(connectionStatusReceiver);
    }

    private void initView() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        drawerAdapter = new DrawerAdapter(this, navigationView, drawerLayout, toolbar);
        drawerAdapter.attachDrawer();

        deckName.setText(deck.deckName);
        questionNo.setText(getString(R.string.question_no, cardCounter));
        correctAnswers.setText(getString(
                R.string.correct_answers, correctAnswersCounter, noOfQuestions));
        adapterViewPager =
                new DeckPagerAdapter(getSupportFragmentManager(), deck, 4, this);
        viewPager.setAdapter(adapterViewPager);
    }

    private void setVariables(){
        noOfQuestions = deck.numberOfQuestions;
        cardCounter = 1;
    }

    private void updateCard(boolean addCorrectAnswer){
        updateCounters(addCorrectAnswer);
        setCard();
    }

    private void setCard(){
        if(cardCounter - 1  == noOfQuestions)
            displayResult();
        else
            displayNextCard();
    }

    private void displayNextCard() {
        viewPager.setCurrentItem(0, false);
        adapterViewPager.changeData();
        questionNo.setText(getString(R.string.question_no, cardCounter));
        correctAnswers.setText(getString(
                R.string.correct_answers, correctAnswersCounter, noOfQuestions));
    }

    private void displayResult(){
        ResultDialogFragment resultDialog = ResultDialogFragment.newInstance(
                correctAnswersCounter, deck.numberOfQuestions);
        resultDialog.show(getSupportFragmentManager(), "result");
    }

    private void restarExam(){
        setInitialValues();
        adapterViewPager.onResultDisplay();
        setFirstCard();
    }

    private void updateCounters(boolean addCorrectAnswer){
        updateCorrectAnswersCounter(addCorrectAnswer);
        cardCounter++;
    }

    private void updateCorrectAnswersCounter(boolean addCorrectAnswer){
        if(addCorrectAnswer)
            correctAnswersCounter++;
    }

    private void setInitialValues(){
        cardCounter = 1;
        correctAnswersCounter = 0;
    }

    private void setFirstCard() {
        questionNo.setText(getString(R.string.question_no, 1));
        correctAnswers.setText(getString(
                R.string.correct_answers, correctAnswersCounter, noOfQuestions));
        viewPager.setCurrentItem(0, false);
    }
    //---------------------------

    @Override
    public void onMoveToNextCard(boolean addCorrectAnswer) {
        updateCard(addCorrectAnswer);
    }

    @Override
    public void onResultShow(){
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

    private void populateDeck() {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_toolbar_menu, menu);
        return true;
    }
    //------For testing------
}
