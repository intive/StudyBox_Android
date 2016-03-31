package com.blstream.studybox.model.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by Łukasz on 2016-03-17.
 */

@Table(name = "Cards")
public class Card extends Model {

    @Expose
    @Column(name = "QuestionNo")
    public Integer questionNo;

    @Expose
    @Column(name = "Question")
    public String question;

    @Expose
    @Column(name = "Prompt")
    public String prompt;

    @Expose
    @Column(name = "Answer")
    public String answer;

    @Column(name = "Deck", onDelete = Column.ForeignKeyAction.CASCADE)
    public Deck deck;

    public Card() {
        super();
    }

    public Card(Integer questionNo, String question, String prompt, String answer, Deck deck) {
        this.questionNo = questionNo;
        this.question = question;
        this.prompt = prompt;
        this.answer = answer;
        this.deck = deck;
    }

    public static List<Card> all() {
        return new Select().from(Card.class).execute();
    }

    public Integer getQuestionNo() {
        return questionNo;
    }

    public String getQuestion() {
        return question;
    }

    public String getPrompt() {
        return prompt;
    }

    public String getAnswer() {
        return answer;
    }

    public Deck getDeck() {
        return deck;
    }
}
