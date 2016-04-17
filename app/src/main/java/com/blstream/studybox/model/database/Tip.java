package com.blstream.studybox.model.database;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by Oldzi on 06.04.2016.
 */
public class Tip extends Model implements Parcelable {

    @Expose
    @Column(name = "idCard")
    public String idCard;

    @Expose
    @Column(name = "prompt")
    public String prompt;

    public Tip() {
        super();
    }

    public Tip(String idCard, String prompt) {
        this.idCard = idCard;
        this.prompt = prompt;
    }

    public static List<Tip> all() {
        return new Select().from(Tip.class).execute();
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idCard);
        dest.writeString(prompt);
    }

    public static final Creator<Tip> CREATOR = new Creator<Tip>() {
        @Override
        public Tip createFromParcel(Parcel source) {
            return new Tip(source);
        }

        @Override
        public Tip[] newArray(int size) {
            return new Tip[size];
        }
    };

    private Tip(Parcel source) {
        idCard = source.readString();
        prompt = source.readString();
    }
}
