package com.blstream.studybox.model.database;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;

import java.util.List;

public class Tip extends Model implements Parcelable {

    @Expose
    @Column(name = "idCard")
    public String idCard;

    @Expose
    @Column(name = "essence")
    public String essence;

    @Expose
    @Column(name = "difficult")
    public int difficult;

    public Tip() {
        super();
    }

    public Tip(String idCard, String essence, int difficult) {
        this.idCard = idCard;
        this.essence = essence;
        this.difficult = difficult;
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

    public String getEssence() {
        return essence;
    }

    public void setEssence(String essence) {
        this.essence = essence;
    }

    public int getDifficult() {
        return difficult;
    }

    public void setDifficult(int difficult) {
        this.difficult = difficult;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idCard);
        dest.writeString(essence);
        dest.writeInt(difficult);
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
        essence = source.readString();
        difficult = source.readInt();
    }
}
