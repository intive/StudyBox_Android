package com.blstream.studybox.model.database;

import android.os.Parcel;
import android.os.Parcelable;

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
public class Card extends Model implements Parcelable{

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(questionNo);
        dest.writeString(question);
        dest.writeString(prompt);
        dest.writeString(answer);
    }

    public static final Creator<Card> CREATOR = new Creator<Card>() {
        @Override
        public Card createFromParcel(Parcel source) {
            return new Card(source);
        }

        @Override
        public Card[] newArray(int size) {
            return new Card[size];
        }
    };

    private Card(Parcel source) {
        questionNo = source.readInt();
        question = source.readString();
        prompt = source.readString();
        answer = source.readString();
    }
}
