package com.blstream.studybox.model.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;

import java.text.Collator;
import java.util.List;
import java.util.Locale;

@Table(name = "Decks")
public class Deck extends Model implements Comparable<Deck> {
    @Expose
    @Column(name = "deckId", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private String id;
    @Expose
    @Column(name = "name")
    private String name;
    @Expose
    @Column(name = "isPublic")
    private Boolean isPublic;
    @Expose
    @Column(name = "creatorEmail")
    private String creatorEmail;
    @Expose
    @Column(name = "flashcardsCount")
    private int flashcardsCount;

    public Deck() {
        super();
    }

    public Deck(String id, String name, Boolean isPublic) {
        this.id = id;
        this.name = name;
        this.isPublic = isPublic;
    }

    public static List<Deck> getAll() {
        return new Select()
                .from(Deck.class)
                .execute();
    }

    public String getDeckId() {
        return id;
    }

    public void setDeckId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public String getCreatorEmail() {
        return creatorEmail;
    }

    public void setCreatorEmail(String creatorEmail) {
        this.creatorEmail = creatorEmail;
    }

    public int getFlashcardsCount() {
        return flashcardsCount;
    }

    public void setFlashcardsCount(int flashcardsCount) {
        this.flashcardsCount = flashcardsCount;
    }

    @Override
    public int compareTo(Deck another) {
        Collator collator = Collator.getInstance(Locale.getDefault());
        return collator.compare(name, another.getName());
    }
}

