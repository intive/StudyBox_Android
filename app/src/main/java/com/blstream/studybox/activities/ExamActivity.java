package com.blstream.studybox.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.blstream.studybox.components.DrawerAdapter;
import com.blstream.studybox.R;
import com.blstream.studybox.exam_view.DeckPagerAdapter;
import com.blstream.studybox.exam_view.DeckViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ExamActivity extends AppCompatActivity {

    @Bind(R.id.deckName)
    public TextView deckName;

    @Bind(R.id.questionNo)
    public TextView questionNo;

    @Bind(R.id.correctAnswers)
    public TextView correctAnswers;

    @Bind(R.id.vpPager)
    public DeckViewPager viewPager;

    @Bind(R.id.toolbar_exam)
    Toolbar toolbar;

    @Bind(R.id.nav_view_exam)
    NavigationView navigationView;

    @Bind(R.id.drawer_layout_exam)
    DrawerLayout drawerLayout;

    private DeckPagerAdapter adapterViewPager;
    private int pageCounter;
    private int correctAnswersCounter;
    private int totalPages;
    private Integer noOfQuestions;
    DrawerAdapter drawerAdapter;

    //------For testing------
    final List<Card> questions = new ArrayList<>();
    private Deck deck;
    //------For testing------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        //------For testing------
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
        //------For testing------
        setVariables();
        initView();
    }

    public void setVariables(){
        noOfQuestions = deck.numberOfQuestions;
        totalPages = noOfQuestions * 2 + 1;
    }

    private void initView() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        Context context = getApplicationContext();      //only For testing
        drawerAdapter = new DrawerAdapter(navigationView, drawerLayout, toolbar, context);
        drawerAdapter.attachDrawer();

        deckName.setText(deck.deckName);
        questionNo.setText(getString(R.string.question_no, pageCounter/2 + 1));
        correctAnswers.setText(getString(
                R.string.correct_answers, correctAnswersCounter, noOfQuestions));
        adapterViewPager =
                new DeckPagerAdapter(getSupportFragmentManager(), deck);
        viewPager.setAdapter(adapterViewPager);
        viewPager.setOffscreenPageLimit(5);


    }

    public void showNextPage(View view) {
            pageCounter+=2;
            updateCorrectAnswersCounter(view);
            viewPager.setCurrentItem(pageCounter, false);
            setPageData(view);
    }

    public void updateCorrectAnswersCounter(View view){
        if(view.getId() == R.id.correct_ans_btn)
            correctAnswersCounter++;
    }

    public void setPageData(View view){
        if(pageCounter == totalPages - 1)
            setResultPageData(view);
        else
            setQuestionPageData(view);
    }

    public void setResultPageData(View view) {
        adapterViewPager.setTotalScore(correctAnswersCounter);
        setDeckInfoVisibility(View.GONE);
    }

    public void setQuestionPageData(View view){
        questionNo.setText(getString(R.string.question_no, pageCounter / 2 + 1));
        correctAnswers.setText(getString(
                R.string.correct_answers, correctAnswersCounter, noOfQuestions));
    }

    public void setDeckInfoVisibility(int visibility){
        deckName.setVisibility(visibility);
        questionNo.setVisibility(visibility);
        correctAnswers.setVisibility(visibility);
    }

    public void restartExam(View view) {
        setInitialValues();
        setDeckInfoVisibility(View.VISIBLE);
        viewPager.setCurrentItem(pageCounter, false);
    }

    public void setInitialValues(){
        pageCounter = 0;
        correctAnswersCounter = 0;
        questionNo.setText(getString(R.string.question_no, 1));
        correctAnswers.setText(getString(
                R.string.correct_answers, correctAnswersCounter, noOfQuestions));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_toolbar_menu, menu);
        return true;
    }
    //------For testing------
}
