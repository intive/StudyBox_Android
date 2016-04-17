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
    @Column(name = "flashcardId")
    public String id;

    @Expose
    @Column(name = "question")
    public String question;

    @Expose
    @Column(name = "answer")
    public String answer;

    @Expose
    @Column(name = "deckId")
    public String deckId;

    public Card() {
        super();
    }

    public Card(String id, String question, String answer, String deckId) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.deckId = deckId;
    }

    public static List<Card> all() {
        return new Select().from(Card.class).execute();
    }

    public String getDeckId() {
        return deckId;
    }

    public String getCardId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(question);
        dest.writeString(answer);
        dest.writeString(deckId);
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
        id = source.readString();
        question = source.readString();
        answer = source.readString();
        deckId = source.readString();
    }
}
