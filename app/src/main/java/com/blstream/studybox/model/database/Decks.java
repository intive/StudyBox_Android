package com.blstream.studybox.model.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by Bartosz Kozajda on 03.04.2016.
 */
@Table(name = "Decks")
public class Decks extends Model {
    @Expose
    @Column(name = "deckId", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private String id;
    @Expose
    @Column(name = "name")
    private String name;
    @Expose
    @Column(name = "isPublic")
    private Boolean isPublic;

    public Decks() {
        super();
    }

    public Decks(String id, String name, Boolean isPublic) {
        this.id = id;
        this.name = name;
        this.isPublic = isPublic;
    }

    public static List<Decks> getAll() {
        return new Select()
                .from(Decks.class)
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

    public void setIsPublic(Boolean isPublic) { this.isPublic = isPublic; }
}

