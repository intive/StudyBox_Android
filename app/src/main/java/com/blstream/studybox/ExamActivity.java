package com.blstream.studybox;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.blstream.studybox.fragment.DeckPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ExamActivity extends AppCompatActivity {

    static List<TestClass> questions = new ArrayList<>();

    ViewPager viewPager;
    DeckPagerAdapter adapterViewPager;
    int pageCounter;
    int correctAnswersCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        questions.add(new TestClass("Pytanie", "Odpowiedz"));
        questions.add(new TestClass("Czesc", "Hej"));
        questions.add(new TestClass("Ile?", "Tyle"));
        questions.add(new TestClass("Co?", "To"));

        initView();
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.vpPager);
        adapterViewPager =
                new DeckPagerAdapter(getSupportFragmentManager(), questions);
        viewPager.setAdapter(adapterViewPager);
    }

    public void showNextQuestion(View view) {
        if(pageCounter < 3) {
            pageCounter++;
            TestClass temp = questions.get(pageCounter);
            adapterViewPager.getQuestionDataUpdater().changeData(temp.question);
            adapterViewPager.getAnswerDataUpdater().changeData(temp.answer);
            viewPager.setCurrentItem(0, false); //Set Fragment to be displayed and disable smooth scroll
            Log.d("pageCounter", String.valueOf(pageCounter));
            updateCorrectAnswersCounter(view);
        }
    }

    public void updateCorrectAnswersCounter(View view){
        if(view.getId() == R.id.correct_ans_btn)
            correctAnswersCounter++;
    }

    public class TestClass{
        public String question;
        public String answer;

        public TestClass(String que, String ans){
            question = que;
            answer = ans;
        }
    }

}
